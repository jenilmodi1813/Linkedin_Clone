package com.linkedin.user_service.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public ApiException(String message,HttpStatus status){
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
