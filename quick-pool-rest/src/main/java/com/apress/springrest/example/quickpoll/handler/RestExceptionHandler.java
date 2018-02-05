package com.apress.springrest.example.quickpoll.handler;

import com.apress.springrest.example.quickpoll.dto.ErrorResponseDetail;
import com.apress.springrest.example.quickpoll.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResponseNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        ErrorResponseDetail error = new ErrorResponseDetail();
        error.setTimestamp(new Date().getTime());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTitle("Resource Not Found!");
        error.setDetail(exception.getMessage());
        error.setDeveloperMessage(exception.getClass().getName());

        return new ResponseEntity<Object>(error, null, HttpStatus.NOT_FOUND);
    }
}
