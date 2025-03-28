package com.adminservicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.adminsecurity.service.UserDetailsImpl;

public class UserDetailsImplTest {

    @Test
    public void testUserDetailsImpl() {
        // ✅ Test data
        String id = "1";
        String username = "user";
        String email = "user@example.com";
        String password = "password";
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // ✅ Create UserDetailsImpl object with authorities
        UserDetailsImpl userDetails = new UserDetailsImpl(id, username, email, password, authorities);

        // ✅ Assert statements
        assertEquals(id, userDetails.getId());
        assertEquals(username, userDetails.getUsername());
        assertEquals(email, userDetails.getEmail());
        assertEquals(password, userDetails.getPassword());

        // ✅ Check authorities
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
