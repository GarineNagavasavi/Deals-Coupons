package com.adminsecurity.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//Represents a request payload for user login
public class LoginRequest 
{
	
	@NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username must be at most 20 characters")
	private String userName;

	@NotNull(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;

	// Getters and setters
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
