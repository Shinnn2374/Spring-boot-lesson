package com.example.JWT_auth_example.exception;

public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException(String token ,String message) {
        super(message.format("Error trying to refresh by token - " + token));
    }

    public RefreshTokenException(String message) {
        super(message);
    }
}
