package com.media.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(GlobalRunTimeException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

}

