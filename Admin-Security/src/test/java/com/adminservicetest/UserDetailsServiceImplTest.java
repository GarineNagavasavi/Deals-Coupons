package com.adminservicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.adminsecurity.model.Admin;
import com.adminsecurity.repository.AdminRepository;
import com.adminsecurity.service.UserDetailsServiceImpl;

public class UserDetailsServiceImplTest {

    @Mock
    private AdminRepository adminRepositoryMock;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "user";
        Admin admin = new Admin("1", username, "password", "Leesha");

        // Mock the findByUsername method to return an Optional containing the admin object
        when(adminRepositoryMock.findByUsername(username)).thenReturn(Optional.of(admin));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals(admin.getUsername(), userDetails.getUsername(), "Usernames should match");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "nonexistent_user";

        // Mock the behavior of findByUsername method to return Optional.empty() for a nonexistent user
        when(adminRepositoryMock.findByUsername(username)).thenReturn(Optional.empty());

        // Act and Assert
        // Expecting a UsernameNotFoundException to be thrown when the user is not found
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username),
                "Expected UsernameNotFoundException when user is not found");
    }
}
