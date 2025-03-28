package com.adminsecurity.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//Service interface for loading user details by username
public interface UserDetailsService {

	 // Load user details by username
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}


