package com.ProjectsManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException,badRequest);
    }

    @ExceptionHandler(value = {ApiDuplicatedLoginException.class})
    public ResponseEntity<Object> handleApiDuplicatedLoginException(ApiDuplicatedLoginException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException,badRequest);
    }

    @ExceptionHandler(value = {ApiInvalidTokenException.class})
    public ResponseEntity<Object> handleApiInvalidTokenException(ApiInvalidTokenException e){
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                e.getMessage(),
                unauthorized,
                LocalDateTime.now());
        return new ResponseEntity<>(apiException,unauthorized);
    }
    @ExceptionHandler(value = {ApiUserEmailException.class})
    public ResponseEntity<Object> ApiUserEmailException(ApiUserEmailException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException,badRequest);
    }
}
