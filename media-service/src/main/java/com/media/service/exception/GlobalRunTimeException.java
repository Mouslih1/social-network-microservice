package com.media.service.exception;

import org.springframework.http.HttpStatus;

public class GlobalRunTimeException extends RuntimeException{
    private HttpStatus status ;

    public GlobalRunTimeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
