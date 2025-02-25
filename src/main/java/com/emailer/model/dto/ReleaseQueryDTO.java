package com.emailer.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReleaseQueryDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String releaseType;
} 