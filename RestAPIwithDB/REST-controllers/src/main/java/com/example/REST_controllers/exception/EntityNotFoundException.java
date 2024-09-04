package com.example.REST_controllers.exception;

public class EntityNotFoundException extends RuntimeException
{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
