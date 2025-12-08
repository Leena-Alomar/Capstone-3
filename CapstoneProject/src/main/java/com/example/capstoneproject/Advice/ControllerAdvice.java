package com.example.capstoneproject.Advice;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.API.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ControllerAdvice {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> ApiException(ApiException apiException){
        String message= apiException.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }
}
