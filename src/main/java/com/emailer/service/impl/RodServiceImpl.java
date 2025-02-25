package com.emailer.service.impl;

import com.emailer.exception.RodIntegrationException;
import com.emailer.model.dto.ReleaseDTO;
import com.emailer.model.dto.ReleaseQueryDTO;
import com.emailer.service.RodService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RodServiceImpl implements RodService {

    private final RestTemplate restTemplate;
    private final String rodApiBaseUrl;

    public RodServiceImpl(RestTemplate restTemplate, @Value("${rod.api.base-url}") String rodApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.rodApiBaseUrl = rodApiBaseUrl;
    }

    @Override
    @Cacheable(value = "releases", key = "#queryDTO")
    @Retryable(value = {RodIntegrationException.class}, maxAttempts = 3)
    public List<ReleaseDTO> getReleases(ReleaseQueryDTO queryDTO) {
        try {
            String url = buildQueryUrl(queryDTO);
            ResponseEntity<ReleaseDTO[]> response = restTemplate.getForEntity(url, ReleaseDTO[].class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
            
            return Collections.emptyList();
        } catch (Exception e) {
            throw new RodIntegrationException("Failed to fetch releases from ROD: " + e.getMessage());
        }
    }

    private String buildQueryUrl(ReleaseQueryDTO queryDTO) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(rodApiBaseUrl + "/releases")
            .queryParam("startDate", queryDTO.getStartDate())
            .queryParam("endDate", queryDTO.getEndDate());

        if (queryDTO.getReleaseType() != null) {
            builder.queryParam("releaseType", queryDTO.getReleaseType());
        }

        return builder.toUriString();
    }
} 