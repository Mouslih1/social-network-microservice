package com.media.service.exception;

import org.springframework.http.HttpStatus;

import java.io.IOException;

public class StorageException extends GlobalRunTimeException {

    public StorageException(String message, IOException e) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}