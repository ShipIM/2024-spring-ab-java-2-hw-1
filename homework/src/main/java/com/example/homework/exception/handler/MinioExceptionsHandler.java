package com.example.homework.exception.handler;

import io.minio.errors.ErrorResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class MinioExceptionsHandler {

    @ExceptionHandler(value = ErrorResponseException.class)
    public ResponseEntity<Map<String,String>> handleCarNotFoundException(ErrorResponseException exception) {
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.NOT_FOUND);
    }

}
