package com.example.demo.utility;

import com.example.demo.constants.StatusCode;
import com.example.demo.response.GlobalResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": "+ error.getDefaultMessage());
        }

        for(ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": "+error.getDefaultMessage());
        }

        GlobalResponse globalResponse = GlobalResponse.builder()
                .message(StatusCode.BAD_REQUEST.getMessage())
                .statusCode(StatusCode.BAD_REQUEST.getCode())
                .errors(errors)
                .build();

        return handleExceptionInternal(ex, globalResponse,headers, HttpStatus.BAD_REQUEST, request);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> errors = new ArrayList<>();
        errors.add(ex.getLocalizedMessage());
        GlobalResponse globalResponse = GlobalResponse.builder()
                .message(StatusCode.BAD_REQUEST.getMessage())
                .statusCode(StatusCode.BAD_REQUEST.getCode())
                .errors(errors)
                .build();

        return handleExceptionInternal(ex, globalResponse,headers, HttpStatus.BAD_REQUEST, request);
    }
}
