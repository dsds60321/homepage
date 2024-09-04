package com.gh.global.controller;

import com.gh.global.dto.response.ApiResponse;
import com.gh.global.dto.response.ApiResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ExceptionHndler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleAll(RuntimeException e) {
        return ApiResponse.SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();

        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ApiResponse.BAD_REQUEST("잘못된 요청이 들어왔습니다.", errors);
    }
}
