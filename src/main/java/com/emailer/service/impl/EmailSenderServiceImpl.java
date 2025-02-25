package com.emailer.service.impl;

import com.emailer.model.Email;
import com.emailer.service.EmailSenderService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;
    private final TaskScheduler taskScheduler;

    public EmailSenderServiceImpl(JavaMailSender mailSender, TaskScheduler taskScheduler) {
        this.mailSender = mailSender;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void sendEmail(Email email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setTo(email.getRecipient());
            helper.setSubject(email.getTitle());
            helper.setText(email.getContent(), true);
            
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void scheduleEmail(Email email, String cronExpression) {
        taskScheduler.schedule(
            () -> sendEmail(email),
            new CronTrigger(cronExpression)
        );
    }
} 