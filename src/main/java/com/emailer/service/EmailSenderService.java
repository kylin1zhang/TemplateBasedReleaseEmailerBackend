package com.emailer.service;

import com.emailer.model.Email;

public interface EmailSenderService {
    void sendEmail(Email email);
    void scheduleEmail(Email email, String cronExpression);
} 