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
    
//    public UserDetails getUserById(Long userId) {
//        return userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
//    }

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
    public Map<String, String> forgotPassword(Map<String, String> requestData) {
        Map<String, String> response = new HashMap<>();
        String email = requestData.get("email");
        String newPassword = requestData.get("newPassword");

        // Check if the email is null or empty
        if (email == null || email.isEmpty()) {
            response.put("message", "Please enter a valid email address.");
            return response;
        }

        // Try to find the user by email
        UserDetails user = userRepository.findByEmail(email);

        // Check if the user exists
        if (user != null) {
            // Update the user's password
            user.setPassword(newPassword);
            UserDetails updatedUser = userRepository.save(user);

            // Generate a new token for the user
            Map<String, String> tokenResponse = jwtGenerator.generateToken(updatedUser);

            // Add the token to the response
            response.putAll(tokenResponse);

            // Add a success message to the response
            response.put("message", "Your password has been updated successfully.");
        } else {
            // If the user is not found, return a message asking for a valid email
            response.put("message", "Please enter a valid email address.");
        }

        return response;
    }
}