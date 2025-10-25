package com.controlacceso.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {
    private String status;
    private String message;
    private T data;
    
    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return ApiResponseDTO.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .build();
    }
    
    public static <T> ApiResponseDTO<T> success(String message) {
        return success(message, null);
    }
    
    public static <T> ApiResponseDTO<T> error(String message) {
        return ApiResponseDTO.<T>builder()
                .status("error")
                .message(message)
                .build();
    }
}

