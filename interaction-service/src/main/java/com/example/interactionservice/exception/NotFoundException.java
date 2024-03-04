package com.example.interactionservice.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message)
    {
        super(message);
    }
}
