package com.emailer.controller;

import com.emailer.model.Template;
import com.emailer.model.dto.ApiResponse;
import com.emailer.model.dto.TemplateDTO;
import com.emailer.service.TemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {
    
    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public ApiResponse<List<Template>> getAllTemplates() {
        return ApiResponse.success(templateService.getAllTemplates());
    }

    @PostMapping
    public ApiResponse<Template> createTemplate(@RequestBody TemplateDTO templateDTO) {
        return ApiResponse.success(templateService.createTemplate(templateDTO));
    }

    @GetMapping("/{id}")
    public ApiResponse<Template> getTemplate(@PathVariable String id) {
        return ApiResponse.success(templateService.getTemplateById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<Template> updateTemplate(@PathVariable String id, 
                                              @RequestBody TemplateDTO templateDTO) {
        return ApiResponse.success(templateService.updateTemplate(id, templateDTO));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTemplate(@PathVariable String id) {
        templateService.deleteTemplate(id);
        return ApiResponse.success(null);
    }
} 