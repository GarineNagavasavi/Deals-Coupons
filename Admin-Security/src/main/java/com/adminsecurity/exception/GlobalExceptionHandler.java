package com.adminsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//Global exception handler to manage exceptions thrown by controllers
@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle exception when admin is not found
    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<Object> handleAdminNotFoundException(AdminNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

 // Handle exception when admin already exists
    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<Object> handleAdminAlreadyExistsException(AdminAlreadyExistsException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

 // Handle exception when admin update fails
    @ExceptionHandler(AdminUpdateException.class)
    public ResponseEntity<Object> handleAdminUpdateException(AdminUpdateException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 // Handle method argument validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError("email");
        String errorMessage;
        if (fieldError != null && fieldError.getDefaultMessage() != null) {
            errorMessage = fieldError.getDefaultMessage();
        } else {
            errorMessage = "Validation failed";
        }
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
 // Handle global exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        String errorMessage = (ex != null ? ex.getMessage() : "Internal Server Error");
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

