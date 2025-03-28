package com.admincontrollertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.adminsecurity.controller.AdminController;
import com.adminsecurity.model.Admin;
import com.adminsecurity.service.AdminServiceImpl;
import org.springframework.web.client.RestTemplate;

public class AdminControllerTest {

    @Mock
    private AdminServiceImpl adminServiceMock;

    @Mock
    private RestTemplate restTemplateMock;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test case for adding a new admin
    @Test
    public void testAddAdmin() {
        // Mock data
        Admin adminToAdd = new Admin("1", "Leesha", "leesha@gmail.com", "password123");
        when(adminServiceMock.addAdmin(any(Admin.class))).thenReturn(adminToAdd);

        // Perform action
        ResponseEntity<Admin> response = adminController.addAdmin(adminToAdd);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(adminToAdd, response.getBody());
    }

    // Test case for getting admin by ID
    @Test
    public void testGetAdminById() {
        // Mock data
        Admin adminToReturn = new Admin("1", "Leesha", "leesha@gmail.com", "password123");
        when(adminServiceMock.findById("1")).thenReturn(adminToReturn);

        // Perform action
        ResponseEntity<Admin> response = adminController.getAdminById("1");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adminToReturn, response.getBody());
    }

    // Test case for updating admin by ID
    @Test
    public void testUpdateById() {
        // Mock data
        Admin updatedAdmin = new Admin("1", "Sailabala", "sailabala@gmail.com", "newpassword123");
        when(adminServiceMock.updateById("1", updatedAdmin)).thenReturn(updatedAdmin);

        // Perform action
        ResponseEntity<Admin> response = adminController.updateById("1", updatedAdmin);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAdmin, response.getBody());
    }

    // Test case for deleting admin by ID
    @Test
    public void testDeleteAdmin() {
        // Perform action
        ResponseEntity<String> response = adminController.deleteAdmin("1");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Admin deleted successfully", response.getBody());
        verify(adminServiceMock, times(1)).deleteById("1");
    }

    // Test case for deleting coupon by ID
    /*@Test
    public void testDeleteCoupon() {
        // Perform action
        ResponseEntity<String> response = adminController.deleteCoupon("123");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted Successfully", response.getBody());
    }*/

    // Test case for deleting Deal by ID
    @Test
    public void testDeleteDeal() {
        // Perform action
        ResponseEntity<String> response = adminController.deleteDeal("456");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deal deleted successfully", response.getBody());
    }
}
