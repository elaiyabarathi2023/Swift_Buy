package com.swiftbuy.user.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtGenerator jwtGenerator;

    public Map<String, String> signupUser(UserDetails userdata) {
        Map<String, String> response = new HashMap<>();

       

        // Save the user
        UserDetails savedUser = userRepository.save(userdata);

        // Generate a token for the user
        Map<String, String> tokenResponse = jwtGenerator.generateToken(savedUser);
        response.putAll(tokenResponse);

        return response;
    }

    public Map<String, String> loginUser(String email, String phoneNumber, String password) {
        Map<String, String> response = new HashMap<>();

        // Initialize user as null
        UserDetails user = null;

        // Try to find the user by email or phone number
        if (email != null && email.contains("@")) {
            user = userRepository.findByEmail(email);
        } else if (phoneNumber != null) {
            user = userRepository.findByPhoneNumber(phoneNumber);
        }

        // Check if the user exists and the password matches
        if (user != null && user.getPassword().equals(password)) {
            // Generate a token for the user
            Map<String, String> tokenResponse = jwtGenerator.generateToken(user);
            response.putAll(tokenResponse);
        } else {
            response.put("message", "Invalid email or phone number, or password");
        }

        return response;
    }
    public Map<String, String> forgotPassword(String email, String newPassword) {
        Map<String, String> response = new HashMap<>();
        // Try to find the user by email
        UserDetails user = userRepository.findByEmail(email);
        // Check if the user exists
        if (user != null) {
            // Update the user's password
            user.setPassword(newPassword);
            userRepository.save(user);
            // Here you would typically have code to send the reset password link to the user's email.
            // As this is a simple example, we will just put a message in the response.
            response.put("message", "Your password has been updated.");
        } else {
            response.put("message", "Invalid email");
        }
        return response;
    }


}