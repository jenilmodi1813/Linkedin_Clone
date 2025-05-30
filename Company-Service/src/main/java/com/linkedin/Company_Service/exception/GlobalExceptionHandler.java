package com.linkedin.Company_Service.exception;

import com.linkedin.Company_Service.dtos.ErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDetails> handleApiException(ApiException apiException, WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(new Date(),apiException.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,apiException.getStatus());
    }
}
