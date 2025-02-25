package com.emailer.controller;

import com.emailer.model.Email;
import com.emailer.model.dto.ApiResponse;
import com.emailer.model.dto.EmailDTO;
import com.emailer.service.EmailService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/emails")
public class EmailController {
    
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/sent")
    public ApiResponse<List<Email>> getSentEmails(
            @RequestParam String userSoeid,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ApiResponse.success(emailService.getSentEmails(userSoeid, startDate, endDate));
    }

    @PostMapping
    public ApiResponse<Email> createDraft(@RequestBody EmailDTO emailDTO) {
        return ApiResponse.success(emailService.createDraft(emailDTO));
    }

    @PostMapping("/{emailId}/save")
    public ApiResponse<Email> saveDraft(
            @PathVariable String emailId,
            @RequestParam String userSoeid) {
        return ApiResponse.success(emailService.saveDraft(emailId, userSoeid));
    }

    @PutMapping("/{emailId}")
    public ApiResponse<Email> updateDraft(
            @PathVariable String emailId,
            @RequestBody EmailDTO emailDTO) {
        return ApiResponse.success(emailService.updateDraft(emailId, emailDTO));
    }

    @GetMapping("/{emailId}/preview")
    public ApiResponse<Email> previewDraft(@PathVariable String emailId) {
        return ApiResponse.success(emailService.getDraftPreview(emailId));
    }

    @PostMapping("/{emailId}/publish")
    public ApiResponse<Void> publishEmail(
            @PathVariable String emailId,
            @RequestParam String userSoeid) {
        emailService.publishEmail(emailId, userSoeid);
        return ApiResponse.success(null);
    }

    @PostMapping("/{emailId}/schedule")
    public ApiResponse<Void> scheduleEmail(
            @PathVariable String emailId,
            @RequestParam String userSoeid,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime sendTime) {
        emailService.scheduleEmail(emailId, userSoeid, sendTime);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{emailId}")
    public ApiResponse<Void> deleteEmail(@PathVariable String emailId) {
        emailService.deleteEmail(emailId);
        return ApiResponse.success(null);
    }
} 