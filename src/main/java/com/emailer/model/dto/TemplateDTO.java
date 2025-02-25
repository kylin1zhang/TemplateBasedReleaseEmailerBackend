package com.emailer.model.dto;

import lombok.Data;

@Data
public class TemplateDTO {
    private String templateName;
    private String templateContent;
    private String templateType;  // "html" or "dragdrop"
    private String layout;  // JSON string for drag-drop layout data
} 