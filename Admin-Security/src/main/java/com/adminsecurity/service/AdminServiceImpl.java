package com.adminsecurity.service;

import com.adminsecurity.exception.AdminAlreadyExistsException;
import com.adminsecurity.exception.AdminNotFoundException;
import com.adminsecurity.model.Admin;
import com.adminsecurity.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Admin findById(String id) {
        logger.info("Finding admin with ID: {}", id);
        return adminRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = "Admin not found with id: " + id;
                    logger.error(errorMessage);
                    return new AdminNotFoundException(errorMessage);
                });
    }

    @Override
    public Admin addAdmin(Admin admin) {
        logger.info("Adding admin with username: {}", admin.getUsername());
        
        if (adminRepository.existsByUsername(admin.getUsername())) {
            String errorMessage = "Admin with username " + admin.getUsername() + " already exists.";
            logger.error(errorMessage);
            throw new AdminAlreadyExistsException(errorMessage);
        }

        // Encrypt the password before saving
        String encryptedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encryptedPassword);

        return adminRepository.save(admin);
    }

    @Override
    public Admin updateById(String id, Admin updatedAdmin) {
        logger.info("Updating admin with ID: {}", id);
        
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = "Admin not found with id: " + id;
                    logger.error(errorMessage);
                    return new AdminNotFoundException(errorMessage);
                });

        // Update fields with updatedAdmin values
        existingAdmin.setUsername(updatedAdmin.getUsername());
        existingAdmin.setName(updatedAdmin.getName());

        // Only update password if it has changed
        if (!updatedAdmin.getPassword().equals(existingAdmin.getPassword())) {
            logger.info("Password for admin with ID: {} has been updated.", id);
            existingAdmin.setPassword(updatedAdmin.getPassword());
        }

        // Encrypt the password before saving the updated admin
        String encryptedPassword = passwordEncoder.encode(existingAdmin.getPassword());
        existingAdmin.setPassword(encryptedPassword);

        return adminRepository.save(existingAdmin);
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        logger.info("Updating admin with username: {}", admin.getUsername());

        // Check if the admin exists in the repository
        Admin existingAdmin = adminRepository.findById(admin.getId())
                .orElseThrow(() -> {
                    String errorMessage = "Admin not found with id: " + admin.getId();
                    logger.error(errorMessage);
                    return new AdminNotFoundException(errorMessage);
                });

        // Update existing admin details
        existingAdmin.setUsername(admin.getUsername());
        existingAdmin.setName(admin.getName());

        // Update the password only if provided
        if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
            logger.info("Password for admin with username: {} has been updated.", admin.getUsername());
            existingAdmin.setPassword(admin.getPassword());
            // Encrypt the new password before saving
            existingAdmin.setPassword(passwordEncoder.encode(existingAdmin.getPassword()));
        }

        // Save and return the updated admin
        return adminRepository.save(existingAdmin);
    }

    @Override
    public void deleteById(String id) {
        logger.info("Deleting admin with ID: {}", id);
        
        adminRepository.findById(id).ifPresentOrElse(admin -> {
            adminRepository.deleteById(id);
            logger.info("Admin with ID: {} deleted successfully.", id);
        }, () -> {
            String errorMessage = "Admin not found with id: " + id;
            logger.error(errorMessage);
            throw new AdminNotFoundException(errorMessage);
        });
    }
}
