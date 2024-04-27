package com.example.UserManagement.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.UserManagement.model.User;

public interface UserRepository extends JpaRepository<User, String>{
    User findByEmail(String email);
}
