package com.adminsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Annotation to indicate that when this exception is thrown, it should result in a 500 Internal Server Error HTTP response
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class AdminUpdateException extends RuntimeException {
	
	// Constructor to create an instance of the exception with a custom error message
    public AdminUpdateException(String message) {
        super(message);
    }
}