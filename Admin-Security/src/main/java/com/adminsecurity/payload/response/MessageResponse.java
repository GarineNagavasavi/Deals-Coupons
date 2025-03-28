package com.adminsecurity.payload.response;

//Represents a generic message response
public class MessageResponse 
{
  private String message;
  
  	//Constructor
	public MessageResponse(String message) {
	    this.message = message;
	  }

    // Getter and setter for message

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
