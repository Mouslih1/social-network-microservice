package com.media.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidFileException extends GlobalRunTimeException {
    public InvalidFileException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}