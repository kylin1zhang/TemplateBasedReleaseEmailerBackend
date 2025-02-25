package com.emailer.service.impl;

import com.emailer.model.Template;
import com.emailer.model.dto.TemplateDTO;
import com.emailer.repository.TemplateRepository;
import com.emailer.service.TemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TemplateServiceImpl implements TemplateService {
    
    private final TemplateRepository templateRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    @Override
    @Transactional
    public Template createTemplate(TemplateDTO templateDTO) {
        Template template = new Template();
        BeanUtils.copyProperties(templateDTO, template);
        template.setId(UUID.randomUUID().toString());
        validateTemplate(template);
        return templateRepository.save(template);
    }

    @Override
    public Template getTemplateById(String id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));
    }

    @Override
    @Transactional
    public Template updateTemplate(String id, TemplateDTO templateDTO) {
        Template existingTemplate = getTemplateById(id);
        BeanUtils.copyProperties(templateDTO, existingTemplate);
        validateTemplate(existingTemplate);
        return templateRepository.save(existingTemplate);
    }

    @Override
    @Transactional
    public void deleteTemplate(String id) {
        templateRepository.deleteById(id);
    }

    private void validateTemplate(Template template) {
        if (template.getTemplateType() == null || 
            (!template.getTemplateType().equals("html") && 
             !template.getTemplateType().equals("dragdrop"))) {
            throw new RuntimeException("Invalid template type. Must be 'html' or 'dragdrop'");
        }

        if (template.getTemplateContent() == null || template.getTemplateContent().trim().isEmpty()) {
            throw new RuntimeException("Template content cannot be empty");
        }

        if (template.getTemplateType().equals("dragdrop") && 
            (template.getLayout() == null || template.getLayout().trim().isEmpty())) {
            throw new RuntimeException("Layout is required for drag-drop templates");
        }
    }
} 