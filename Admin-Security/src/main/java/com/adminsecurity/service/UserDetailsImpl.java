package com.adminsecurity.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.adminsecurity.model.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String username;
    private String email;
    
    @JsonIgnore
    private String password;
    
    private List<GrantedAuthority> authorities;

    // ✅ Constructor with authorities
    public UserDetailsImpl(String id, String username, String email, String password, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // ✅ Corrected build method
    public static UserDetailsImpl build(Admin admin) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        
        return new UserDetailsImpl(
                admin.getId(),
                admin.getUsername(),
                admin.getEmail(),
                admin.getPassword(),
                authorities  // ✅ Pass authorities here
        );
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl admin = (UserDetailsImpl) o;
        return Objects.equals(id, admin.id);
    }
}
