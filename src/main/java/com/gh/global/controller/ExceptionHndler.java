package com.gh.global.controller;

import com.gh.global.dto.response.ApiResponse;
import com.gh.global.dto.response.ApiResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHndler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleAll(RuntimeException e) {
        return ApiResponse.SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
