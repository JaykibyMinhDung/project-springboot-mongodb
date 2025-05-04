package com.example.mdbspringbootreactive.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class ApiResponse<T> {
    // Getter và Setter
    private String message;
    private int statusCode;
    private T data;

    public ApiResponse(String message, int statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ApiResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = null;
    }
}
