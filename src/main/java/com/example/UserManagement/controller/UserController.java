package com.example.UserManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserManagement.model.LoginBody;
import com.example.UserManagement.model.User;
import com.example.UserManagement.repository.UserRepository;
import com.example.UserManagement.service.Authentication;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class UserController {

    @Autowired
    private Authentication authentication;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginBody loginBody){
        String email = loginBody.getEmail();
        String password = loginBody.getPassword();

        if(authentication.checkUserPassword(email, password)){
            return ResponseEntity.ok("success");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }
        
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        String email = user.getEmail();
        String password = user.getPassword();

        if(userRepository.findByEmail(email)!=null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        user.setPassword(authentication.encryptPassword(password));
        userRepository.save(user);
        return ResponseEntity.ok("user registered");
    }
}
