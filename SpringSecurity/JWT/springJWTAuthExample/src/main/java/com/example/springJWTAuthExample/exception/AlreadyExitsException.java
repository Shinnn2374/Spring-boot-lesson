package com.example.springJWTAuthExample.exception;

public class AlreadyExitsException extends RuntimeException {
    public AlreadyExitsException(String message) {
        super(message);
    }
}
