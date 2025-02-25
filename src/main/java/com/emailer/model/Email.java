package com.emailer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Email {
    @Id
    private String emailId;
    private String userSoeid;
    private String title;
    private String recipient;
    private String templateId;
    private LocalDateTime sendTime;
    private String status;
    private String content;
} 