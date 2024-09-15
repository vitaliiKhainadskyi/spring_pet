package com.spring_pet.app.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIException extends RuntimeException {

    private String errorMessage;
    private int errorCode;
    private HttpStatus httpStatus;

    public APIException(int errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public APIException(int errorCode, String errorMessage, HttpStatus httpStatus, Exception e) {
        super(e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}