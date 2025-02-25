package com.emailer.controller;

import com.emailer.model.dto.ApiResponse;
import com.emailer.model.dto.ReleaseDTO;
import com.emailer.model.dto.ReleaseQueryDTO;
import com.emailer.service.RodService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rod")
public class RodController {
    
    private final RodService rodService;

    public RodController(RodService rodService) {
        this.rodService = rodService;
    }

    @GetMapping("/releases")
    public ApiResponse<List<ReleaseDTO>> getReleases(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) String releaseType) {
        
        ReleaseQueryDTO queryDTO = new ReleaseQueryDTO();
        queryDTO.setStartDate(startDate);
        queryDTO.setEndDate(endDate);
        queryDTO.setReleaseType(releaseType);
        
        return ApiResponse.success(rodService.getReleases(queryDTO));
    }
} 