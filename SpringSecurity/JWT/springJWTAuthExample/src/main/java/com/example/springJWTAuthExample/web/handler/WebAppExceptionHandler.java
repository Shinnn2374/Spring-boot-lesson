package com.example.springJWTAuthExample.web.handler;

import com.example.springJWTAuthExample.exception.AlreadyExitsException;
import com.example.springJWTAuthExample.exception.EntityNotFoundException;
import com.example.springJWTAuthExample.exception.RefreshTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class WebAppExceptionHandler {

    @ExceptionHandler(value = RefreshTokenException.class)
    public ResponseEntity<ErrorResponseBody> refreshTokenExceptionHandler(RefreshTokenException e, WebRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, e, request);
    }

    @ExceptionHandler(value = AlreadyExitsException.class)
    public ResponseEntity<ErrorResponseBody> alreadyExitsExceptionHandler(AlreadyExitsException e, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST,e,request);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> entityNotFoundExceptionHandler(EntityNotFoundException e, WebRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, e, request);
    }

    private ResponseEntity<ErrorResponseBody> buildResponse(HttpStatus httpStatus, Exception e, WebRequest request) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponseBody.builder()
                        .message(e.getMessage())
                        .description(request.getDescription(false))
                        .build());
    }
}


