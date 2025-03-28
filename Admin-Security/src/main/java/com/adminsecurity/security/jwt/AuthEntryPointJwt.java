package com.adminsecurity.security.jwt;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


//Custom authentication entry point for JWT authentication
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint
{
	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	// Method called when authentication fails
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// Log unauthorized error
		logger.error("Unauthorized error: {}", authException.getMessage());
		
		// Send unauthorized error response
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
		
	}

}
