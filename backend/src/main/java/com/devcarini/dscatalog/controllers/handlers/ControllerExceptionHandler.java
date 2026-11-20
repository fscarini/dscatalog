package com.devcarini.dscatalog.controllers.handlers;

import com.devcarini.dscatalog.controllers.handlers.errors.StandardError;
import com.devcarini.dscatalog.controllers.handlers.errors.ValidationError;
import com.devcarini.dscatalog.services.exceptions.DatabaseException;
import com.devcarini.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Validation Error", request.getRequestURI());
        e.getBindingResult().getFieldErrors().stream()
                .forEach(x -> err.addError(x.getField(), x.getDefaultMessage()));
        return ResponseEntity.status(status).body(err);
    }

}
