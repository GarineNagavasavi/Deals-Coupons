package com.adminsecurity.repository;

import com.adminsecurity.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {  // Assuming the ID type is String

    // Use Optional to safely handle possible null values
    Optional<Admin> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
