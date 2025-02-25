package com.emailer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Template {
    @Id
    private String id;
    private String templateName;
    private String templateContent;
    private String templateType;
    private String layout;
} 