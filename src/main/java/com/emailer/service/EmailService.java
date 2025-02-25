package com.emailer.service;

import com.emailer.model.Email;
import com.emailer.model.dto.EmailDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface EmailService {
    List<Email> getSentEmails(String userSoeid, LocalDateTime startDate, LocalDateTime endDate);
    Email createDraft(EmailDTO emailDTO);
    Email saveDraft(String emailId, String userSoeid);
    Email updateDraft(String emailId, EmailDTO emailDTO);
    Email getDraftPreview(String emailId);
    void publishEmail(String emailId, String userSoeid);
    void scheduleEmail(String emailId, String userSoeid, LocalDateTime sendTime);
    void deleteEmail(String emailId);
} 