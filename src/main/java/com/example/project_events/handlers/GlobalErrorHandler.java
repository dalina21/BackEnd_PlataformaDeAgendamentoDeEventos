package com.example.project_events.handlers;

import com.example.project_events.errors.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerValidationErrors(MethodArgumentNotValidException e){
        Map<String, String> response = new HashMap<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for(FieldError error : fieldErrors){
            response.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handlerInvalidCredentialsException(InvalidCredentialsException e){
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
