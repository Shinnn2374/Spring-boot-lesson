package com.example.jwt_auth_example.excaption;

import java.text.MessageFormat;

public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException(String message, String refreshToken)  {
        super(MessageFormat.format("Error trying to refresh by token: {0}, {1}", message, refreshToken));
    }

  public RefreshTokenException(String message) {
    super(message);
  }
}
