package com.adminsecurity.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adminsecurity.model.Admin;
import com.adminsecurity.payload.request.LoginRequest;
import com.adminsecurity.payload.request.SignupRequest;
import com.adminsecurity.payload.response.JwtResponse;
import com.adminsecurity.payload.response.MessageResponse;
import com.adminsecurity.repository.AdminRepository;
import com.adminsecurity.security.jwt.JwtUtils;
import com.adminsecurity.service.UserDetailsImpl;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*")
@Validated
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    // Endpoint to authenticate a user
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {

        // It performs authentication using the provided username and password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        // This line sets the authentication object into the security context.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // This generates a JWT token for the authenticated user.
        String jwt = jwtUtils.generateJwtToken(authentication);

        // This retrieves user details from the authentication object.
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // This returns a response containing the JWT token along with user details.
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getEmail()));
    }

    // Endpoint to register a new user
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequest signUpRequest) {

        // This checks if the username already exists in the database. If it does, it returns a bad request response.
        if (adminRepository.existsByUsername(signUpRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        // This checks if the email already exists in the database. If it does, it returns a bad request response.
        if (adminRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        // Now assuming the SignupRequest includes the name field too, or you can provide a default.
        Admin admin = new Admin(signUpRequest.getUserName(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getUserName());

        adminRepository.save(admin);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
