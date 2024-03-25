package com.example.homework.exception.handler;

import com.example.homework.exception.UncheckedMinioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class MinioExceptionsHandler {

    @ExceptionHandler(value = UncheckedMinioException.class)
    public ResponseEntity<Map<String, String>> handleMinioException(UncheckedMinioException exception) {
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
