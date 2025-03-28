package com.adminsecurity.service;

import com.adminsecurity.model.Admin;

//Service interface for Admin operations
public interface AdminService {

	 // Find an admin by their ID
    Admin findById(String id);
    
    // Add a new admin
    Admin addAdmin(Admin admin);
    
    // Update an admin by their ID
    Admin updateById(String id, Admin updatedAdmin);
    

    // Update an admin
    Admin updateAdmin(Admin admin);

 // Delete an admin by their ID
	void deleteById(String id);
    
    
}
