package com.emailer.service.impl;

import com.emailer.exception.EmailException;
import com.emailer.model.Email;
import com.emailer.model.Template;
import com.emailer.model.dto.EmailDTO;
import com.emailer.repository.EmailRepository;
import com.emailer.service.EmailSenderService;
import com.emailer.service.EmailService;
import com.emailer.service.TemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {
    
    private final EmailRepository emailRepository;
    private final TemplateService templateService;
    private final EmailSenderService emailSenderService;

    public EmailServiceImpl(EmailRepository emailRepository, 
                          TemplateService templateService,
                          EmailSenderService emailSenderService) {
        this.emailRepository = emailRepository;
        this.templateService = templateService;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<Email> getSentEmails(String userSoeid, LocalDateTime startDate, LocalDateTime endDate) {
        return emailRepository.findByUserSoeidAndSendTimeBetweenAndStatus(
            userSoeid, startDate, endDate, "success");
    }

    @Override
    @Transactional
    public Email createDraft(EmailDTO emailDTO) {
        Email email = new Email();
        BeanUtils.copyProperties(emailDTO, email);
        email.setEmailId(UUID.randomUUID().toString());
        email.setStatus("draft");
        
        // Validate template exists
        Template template = templateService.getTemplateById(emailDTO.getTemplateId());
        
        return emailRepository.save(email);
    }

    @Override
    @Transactional
    public Email saveDraft(String emailId, String userSoeid) {
        Email email = getEmailAndValidateUser(emailId, userSoeid);
        email.setStatus("draft");
        return emailRepository.save(email);
    }

    @Override
    @Transactional
    public Email updateDraft(String emailId, EmailDTO emailDTO) {
        Email existingEmail = getEmailAndValidateUser(emailId, emailDTO.getUserSoeid());
        if (!"draft".equals(existingEmail.getStatus())) {
            throw new EmailException("Only draft emails can be updated");
        }
        
        BeanUtils.copyProperties(emailDTO, existingEmail);
        return emailRepository.save(existingEmail);
    }

    @Override
    public Email getDraftPreview(String emailId) {
        return emailRepository.findById(emailId)
                .orElseThrow(() -> new EmailException("Email not found"));
    }

    @Override
    @Transactional
    public void publishEmail(String emailId, String userSoeid) {
        Email email = getEmailAndValidateUser(emailId, userSoeid);
        validateEmailBeforeSend(email);
        
        email.setStatus("sending");
        emailRepository.save(email);
        
        try {
            emailSenderService.sendEmail(email);
            email.setStatus("success");
        } catch (Exception e) {
            email.setStatus("failed");
            throw new EmailException("Failed to send email: " + e.getMessage());
        } finally {
            emailRepository.save(email);
        }
    }

    @Override
    @Transactional
    public void scheduleEmail(String emailId, String userSoeid, LocalDateTime sendTime) {
        Email email = getEmailAndValidateUser(emailId, userSoeid);
        validateEmailBeforeSend(email);
        
        email.setSendTime(sendTime);
        email.setStatus("scheduled");
        emailRepository.save(email);
        
        String cronExpression = convertToCronExpression(sendTime);
        emailSenderService.scheduleEmail(email, cronExpression);
    }

    @Override
    @Transactional
    public void deleteEmail(String emailId) {
        emailRepository.deleteById(emailId);
    }

    private Email getEmailAndValidateUser(String emailId, String userSoeid) {
        Email email = emailRepository.findById(emailId)
                .orElseThrow(() -> new EmailException("Email not found"));
                
        if (!email.getUserSoeid().equals(userSoeid)) {
            throw new EmailException("Unauthorized access to email");
        }
        
        return email;
    }

    private void validateEmailBeforeSend(Email email) {
        if (email.getRecipient() == null || email.getRecipient().trim().isEmpty()) {
            throw new EmailException("Recipient is required");
        }
        if (email.getContent() == null || email.getContent().trim().isEmpty()) {
            throw new EmailException("Content is required");
        }
    }

    private String convertToCronExpression(LocalDateTime dateTime) {
        return String.format("%d %d %d %d %d ?",
            dateTime.getMinute(),
            dateTime.getHour(),
            dateTime.getDayOfMonth(),
            dateTime.getMonthValue(),
            dateTime.getDayOfWeek().getValue());
    }
} 