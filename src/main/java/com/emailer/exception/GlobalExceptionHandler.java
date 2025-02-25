package com.emailer.exception;

import com.emailer.model.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error("RUNTIME_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(TemplateException.class)
    public ResponseEntity<ApiResponse<Void>> handleTemplateException(TemplateException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error("TEMPLATE_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(ApiResponse.error("INTERNAL_ERROR", "An internal error occurred"));
    }

    @ExceptionHandler(RodIntegrationException.class)
    public ResponseEntity<ApiResponse<Void>> handleRodIntegrationException(RodIntegrationException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error("ROD_INTEGRATION_ERROR", ex.getMessage()));
    }
} 