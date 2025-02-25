package com.emailer.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmailDTO {
    private String userSoeid;
    private String title;
    private String recipient;
    private String templateId;
    private LocalDateTime sendTime;
    private String content;
} 