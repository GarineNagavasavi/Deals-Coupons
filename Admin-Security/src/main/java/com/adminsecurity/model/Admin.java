package com.adminsecurity.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;  // Import UUID class

// Represents an Admin entity mapped to a SQL table
@Entity
@Table(name = "admins")  // Table name in the database
public class Admin {

    @Id
    @Column(name = "id", unique = true)  // Primary key column
    private String id;  // Use String for the ID field

    @Column(name = "username", unique = true)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    private String password;

    @Column(name = "name")
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    // Default constructor
    public Admin() {
    }

    // Constructor with parameters for creating an admin
    public Admin(String username, String email, String password, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Getters and setters for all fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Generate a new UUID as ID before persisting
    @PrePersist
    public void prePersist() {
        // If ID is null (it is null before persistence), generate a UUID string
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    // Override toString method for better readability
    @Override
    public String toString() {
        return "Admin [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", name=" + name + "]";
    }
}
