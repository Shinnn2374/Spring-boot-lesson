package com.example.spring_app_client.exception;

public class ClientException extends RuntimeException {
  public ClientException(String message) {
    super(message);
  }
}
