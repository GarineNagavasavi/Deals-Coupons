package com.admincontrollertest;

import com.adminsecurity.controller.AuthenticationController;
import com.adminsecurity.model.Admin;
import com.adminsecurity.payload.request.LoginRequest;
import com.adminsecurity.payload.request.SignupRequest;
import com.adminsecurity.payload.response.JwtResponse;
import com.adminsecurity.payload.response.MessageResponse;
import com.adminsecurity.repository.AdminRepository;
import com.adminsecurity.security.jwt.JwtUtils;
import com.adminsecurity.service.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AuthenticationController authenticationController;

    private SignupRequest signupRequest;
    private LoginRequest loginRequest;
    private Admin admin;

    @BeforeEach
    public void setUp() {
        signupRequest = new SignupRequest();
        signupRequest.setUserName("newUser");
        signupRequest.setEmail("newuser@gmail.com");
        signupRequest.setPassword("validPassword123");

        loginRequest = new LoginRequest();
        loginRequest.setUserName("newUser");
        loginRequest.setPassword("validPassword123");

        admin = new Admin("newUser", "newuser@gmail.com", "encodedPassword", "newUser");

        MockitoAnnotations.openMocks(this);
    }




    @Test
    public void testAuthenticateUser_Failure_InvalidCredentials() {
        // Arrange
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Authentication failed"));

        // Act
        try {
            authenticationController.authenticateUser(loginRequest);
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            // Assert
            assertEquals("Authentication failed", e.getMessage());
        }
    }

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        when(adminRepository.existsByUsername("newUser")).thenReturn(false);
        when(adminRepository.existsByEmail("newuser@gmail.com")).thenReturn(false);
        when(encoder.encode("validPassword123")).thenReturn("encodedPassword");
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        // Act
        ResponseEntity<?> response = authenticationController.registerUser(signupRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals("User registered successfully!", messageResponse.getMessage());
    }

    @Test
    public void testRegisterUser_UsernameTaken() {
        // Arrange
        when(adminRepository.existsByUsername("newUser")).thenReturn(true);

        // Act
        ResponseEntity<?> response = authenticationController.registerUser(signupRequest);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals("Error: Username is already taken!", messageResponse.getMessage());
    }

    @Test
    public void testRegisterUser_EmailTaken() {
        // Arrange
        when(adminRepository.existsByUsername("newUser")).thenReturn(false);
        when(adminRepository.existsByEmail("newuser@gmail.com")).thenReturn(true);

        // Act
        ResponseEntity<?> response = authenticationController.registerUser(signupRequest);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals("Error: Email is already in use!", messageResponse.getMessage());
    }
}
