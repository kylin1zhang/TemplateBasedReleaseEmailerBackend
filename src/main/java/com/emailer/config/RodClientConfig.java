package com.emailer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RodClientConfig {
    
    @Value("${rod.api.base-url}")
    private String rodApiBaseUrl;
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public String rodApiBaseUrl() {
        return rodApiBaseUrl;
    }
} 