package com.ProjectsManagementSystem.exception;


public class ApiUserEmailException extends RuntimeException {
    public ApiUserEmailException(String message) {
        super(message);
    }
    public ApiUserEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
