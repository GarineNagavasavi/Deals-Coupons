package com.adminservicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.adminsecurity.exception.AdminAlreadyExistsException;
import com.adminsecurity.exception.AdminNotFoundException;
import com.adminsecurity.model.Admin;
import com.adminsecurity.repository.AdminRepository;
import com.adminsecurity.service.AdminServiceImpl;

@ExtendWith(MockitoExtension.class)  // JUnit 5 style initialization for Mockito
class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;

    @BeforeEach
    void setUp() {
        // Initialize mock Admin object to be used in multiple tests
        admin = new Admin("1", "Leesha", "leesha@123", "Leesha Name");
    }

    private void mockFindAdminById(String id, Optional<Admin> adminOptional) {
        when(adminRepository.findById(id)).thenReturn(adminOptional);
    }

    private void mockExistsByUsername(String username, boolean exists) {
        when(adminRepository.existsByUsername(username)).thenReturn(exists);
    }

    @Test
    void findById_AdminExists_ReturnsAdmin() {
        // Arrange
        mockFindAdminById("1", Optional.of(admin));

        // Act
        Admin foundAdmin = adminService.findById("1");

        // Assert
        assertNotNull(foundAdmin);
        assertEquals(admin.getId(), foundAdmin.getId());
        assertEquals(admin.getUsername(), foundAdmin.getUsername());
        assertEquals(admin.getPassword(), foundAdmin.getPassword());
        assertEquals(admin.getName(), foundAdmin.getName());
    }

    @Test
    void findById_AdminNotExists_ThrowsAdminNotFoundException() {
        // Arrange
        mockFindAdminById("1", Optional.empty());

        // Act and Assert
        assertThrows(AdminNotFoundException.class, () -> adminService.findById("1"));
    }

    @Test
    void addAdmin_NewAdmin_AddsAdmin() {
        // Arrange
        Admin newAdmin = new Admin("2", "Sailabala", "saila@321", "Sailabala Name");
        mockExistsByUsername(newAdmin.getUsername(), false);
        when(passwordEncoder.encode(newAdmin.getPassword())).thenReturn("encryptedPassword");
        when(adminRepository.save(any(Admin.class))).thenReturn(newAdmin);

        // Act
        Admin addedAdmin = adminService.addAdmin(newAdmin);

        // Assert
        assertNotNull(addedAdmin);
        assertEquals(newAdmin.getId(), addedAdmin.getId());
        assertEquals(newAdmin.getUsername(), addedAdmin.getUsername());
        assertEquals("encryptedPassword", addedAdmin.getPassword());  // Validate encrypted password
        assertEquals(newAdmin.getName(), addedAdmin.getName());
    }

    @Test
    void addAdmin_AdminAlreadyExists_ThrowsAdminAlreadyExistsException() {
        // Arrange
        mockExistsByUsername(admin.getUsername(), true);

        // Act and Assert
        assertThrows(AdminAlreadyExistsException.class, () -> adminService.addAdmin(admin));
    }

    @Test
    void updateById_AdminExists_UpdatesAdmin() {
        // Arrange
        Admin updatedAdmin = new Admin("1", "Leesha Updated", "leeshaupdated@123", "Updated Name");

        mockFindAdminById("1", Optional.of(admin));
        when(adminRepository.save(any(Admin.class))).thenReturn(updatedAdmin);

        // Act
        Admin result = adminService.updateById("1", updatedAdmin);

        // Assert
        assertNotNull(result);
        assertEquals(updatedAdmin.getId(), result.getId());
        assertEquals(updatedAdmin.getUsername(), result.getUsername());
        assertEquals(updatedAdmin.getEmail(), result.getEmail());
        assertEquals(updatedAdmin.getName(), result.getName());
    }

    @Test
    void updateById_AdminNotFound_ThrowsAdminNotFoundException() {
        // Arrange
        Admin updatedAdmin = new Admin("1", "Leesha Updated", "leeshaupdated@123", "Updated Name");
        mockFindAdminById("1", Optional.empty());

        // Act and Assert
        assertThrows(AdminNotFoundException.class, () -> adminService.updateById("1", updatedAdmin));
    }

    @Test
    void deleteById_AdminExists_DeletesAdmin() {
        // Arrange
        mockFindAdminById("1", Optional.of(admin));

        // Act
        adminService.deleteById("1");

        // Assert
        verify(adminRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteById_AdminNotFound_ThrowsAdminNotFoundException() {
        // Arrange
        mockFindAdminById("1", Optional.empty());

        // Act and Assert
        assertThrows(AdminNotFoundException.class, () -> adminService.deleteById("1"));
    }
}
