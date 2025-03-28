package com.adminsecurity.exception;

import org.springframework.http.HttpStatus;


//Class representing an error response containing HTTP status and message
public class ErrorResponse {
	 // HTTP status of the error response
    private final HttpStatus status;
    
 // Message describing the error
    private final String message;

//     Constructor to initialize ErrorResponse object with HTTP status and message.
//    The HttpStatus code of the error response
//    Message describing the error
    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
//    Get the Http status code of the error response
    public HttpStatus getStatus() {
        return status;
    }
//Get the message describing the error
    public String getMessage() {
        return message;
    }
}
