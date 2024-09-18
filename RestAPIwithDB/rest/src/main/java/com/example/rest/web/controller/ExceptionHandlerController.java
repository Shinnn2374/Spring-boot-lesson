package com.example.rest.web.controller;

import com.example.rest.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.rest.web.model.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController
{
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException e)
    {
        log.error("Error when find entity " + e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getLocalizedMessage()));
    }
}
