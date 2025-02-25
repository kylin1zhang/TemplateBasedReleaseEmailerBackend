package com.emailer.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReleaseDTO {
    private String releaseId;
    private String title;
    private String description;
    private LocalDateTime releaseDate;
} 