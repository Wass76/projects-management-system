package com.ProjectsManagementSystem.exception;

public class ApiInvalidTokenException extends RuntimeException{
    public ApiInvalidTokenException(String message) {
        super(message);
    }
    public ApiInvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
