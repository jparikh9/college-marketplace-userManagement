package com.example.UserManagement.service;
import com.example.UserManagement.model.User;
import com.example.UserManagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Authentication {
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private User user;
    @Autowired
    private UserRepository userRepository;

    public String encryptPassword(String password){
        String encString = passwordEncoder.encode(password);
        return encString;
    }

    public boolean matchPassword(String rawPassword, String encoded){
        return passwordEncoder.matches(rawPassword, encoded);
    }

    public boolean checkUserPassword(String email, String password){
        user = userRepository.findByEmail(email);
        boolean status = false;
        if(user!=null){
            String enc_pwd = user.getPassword();
            status = matchPassword(password, enc_pwd);
        }
        return status;
    }
}
