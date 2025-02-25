package com.emailer.service;

import com.emailer.model.Template;
import com.emailer.model.dto.TemplateDTO;
import java.util.List;

public interface TemplateService {
    List<Template> getAllTemplates();
    Template createTemplate(TemplateDTO templateDTO);
    Template getTemplateById(String id);
    Template updateTemplate(String id, TemplateDTO templateDTO);
    void deleteTemplate(String id);
} 