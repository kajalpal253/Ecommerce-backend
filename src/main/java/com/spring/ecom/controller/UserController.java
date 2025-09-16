package com.spring.ecom.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.spring.ecom.exception.UserException;
import com.spring.ecom.model.User;
import com.spring.ecom.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(Authentication authentication) throws UserException {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email); // direct User return kare

        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }

        return ResponseEntity.ok(user);
    }

}

//they control login ,register