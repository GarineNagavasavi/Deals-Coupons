
package com.adminsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//Annotation to indicate that when this exception is thrown, it should result in a 404 Not Found HTTP response

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AdminNotFoundException extends RuntimeException {
	// Constructor to create an instance of the exception with a custom error message
    public AdminNotFoundException(String message) {
        super(message);
    }
    
    
}
