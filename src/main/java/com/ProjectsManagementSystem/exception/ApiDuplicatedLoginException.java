package com.ProjectsManagementSystem.exception;

public class ApiDuplicatedLoginException extends RuntimeException{
    public ApiDuplicatedLoginException(String message){
        super(message);
    }
    public ApiDuplicatedLoginException(String message, Throwable cause){
        super(message, cause);
    }
}
