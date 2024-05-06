package com.swiftbuy.admin.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.AdminDetails;
import com.swiftbuy.admin.repository.AdminRepository;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.repository.UserRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtGenerator jwtGenerator;

    public Map<String, String> signupUser(AdminDetails userdata) {
        Map<String, String> response = new HashMap<>();

       

        // Save the user
        AdminDetails savedUser = adminRepository.save(userdata);

        // Generate a token for the user
        Map<String, String> tokenResponse = jwtGenerator.generateToken(savedUser);
        response.putAll(tokenResponse);

        return response;
    }

    public Map<String, String> loginUser(String username) {
        Map<String, String> response = new HashMap<>();

        // Try to find the user by username
        AdminDetails user = adminRepository.findByUsername(username);

        // Check if the user exists
        if (user != null) {
            // Generate a token for the user
            Map<String, String> tokenResponse = jwtGenerator.generateToken(user);
            response.putAll(tokenResponse);
        } else {
            response.put("message", "Invalid username");
        }

        return response;
    }
    public Map<String, String> forgotPassword(Map<String, String> requestData) {
        Map<String, String> response = new HashMap<>();
        String username = requestData.get("username");
        String newPassword = requestData.get("newPassword");

        // Check if the email is null or empty
        if (username == null || username.isEmpty()) {
            response.put("message", "Please enter a valid email address.");
            return response;
        }

        // Try to find the user by email
        AdminDetails user = adminRepository.findByEmail(username);

        // Check if the user exists
        if (user != null) {
            // Update the user's password
            user.setPassword(newPassword);
            AdminDetails updatedUser = adminRepository.save(user);

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