package com.emailer.model.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String status;
    private T data;
    private String errorCode;
    private String message;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus("success");
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> error(String errorCode, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus("error");
        response.setErrorCode(errorCode);
        response.setMessage(message);
        return response;
    }
} 