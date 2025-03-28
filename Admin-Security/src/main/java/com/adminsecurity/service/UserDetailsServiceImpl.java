package com.adminsecurity.service;

import com.adminsecurity.model.Admin;
import com.adminsecurity.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the Admin object using Optional
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);

        // If the admin is not found, throw UsernameNotFoundException
        Admin admin = adminOptional.orElseThrow(() -> new UsernameNotFoundException("Admin not found with username: " + username));

        // Return the UserDetails (UserDetailsImpl is a custom implementation of UserDetails)
        return UserDetailsImpl.build(admin);
    }
}
