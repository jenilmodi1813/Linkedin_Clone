package com.linkedin.Post_Service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String details;


}
