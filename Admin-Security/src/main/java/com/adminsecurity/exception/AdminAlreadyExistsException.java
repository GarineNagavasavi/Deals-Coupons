package com.adminsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Annotation to indicate that when this exception is thrown, it should result in a 409 Conflict HTTP response
@ResponseStatus(value = HttpStatus.CONFLICT)
public class AdminAlreadyExistsException extends RuntimeException {
	
	// Constructor to create an instance of the exception with a custom error message
    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}