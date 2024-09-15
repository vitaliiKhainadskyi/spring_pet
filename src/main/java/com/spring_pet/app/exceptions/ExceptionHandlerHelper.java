package com.spring_pet.app.exceptions;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring_pet.app.ui.model.response.ValidationResponse;

@ControllerAdvice
public class ExceptionHandlerHelper extends ResponseEntityExceptionHandler {
    @ExceptionHandler(APIException.class)
    public ResponseEntity<ValidationResponse> handleCustomException(APIException ex, WebRequest request) {

        ValidationResponse exceptionResponse = new ValidationResponse(new Date(), ex.getErrorCode(),
                ex.getErrorMessage());
        return new ResponseEntity<ValidationResponse>(exceptionResponse,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ValidationResponse> handleAnyException(Exception ex, WebRequest request) {

        String errorMessage = isNotEmpty(ex.getLocalizedMessage()) ? ex.getLocalizedMessage() : ex.toString();

        ValidationResponse exceptionResponse = new ValidationResponse(new Date(), 500, errorMessage);
        return new ResponseEntity<ValidationResponse>(exceptionResponse,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { RuntimeException.class, NullPointerException.class })
    public ResponseEntity<ValidationResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {

        String errorMessage = isNotEmpty(ex.getLocalizedMessage()) ? ex.getLocalizedMessage() : ex.toString();

        ValidationResponse exceptionResponse = new ValidationResponse(new Date(), 500, errorMessage);
        return new ResponseEntity<ValidationResponse>(exceptionResponse,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
