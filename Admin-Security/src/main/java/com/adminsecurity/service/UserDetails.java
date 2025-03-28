package com.adminsecurity.service;

import com.adminsecurity.service.*;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

//Interface representing user details
public interface UserDetails {
	
	// Get the ID of the user
    String getId();
 // Get the username of the user
    String getUsername();
 // Get the email of the user
    String getEmail();
 // Get the password of the user
    String getPassword();
    
 // Get the authorities (roles) granted to the user
    Collection<? extends GrantedAuthority> getAuthorities();
    
 // Check if the user's account is not expired
    boolean isAccountNonExpired();

 // Check if the user's account is not locked
    boolean isAccountNonLocked();
 // Check if the user's credentials are not expired
    boolean isCredentialsNonExpired();
 // Check if the user is enabled
    boolean isEnabled();
}
