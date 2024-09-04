package com.example.REST_controllers.exception;

public class UpdateStateException extends RuntimeException
{
    public UpdateStateException(String message)
    {
        super(message);
    }
}
