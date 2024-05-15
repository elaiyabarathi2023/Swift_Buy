package com.swiftbuy.admin.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.AdminDetails;
import com.swiftbuy.admin.repository.AdminRepository;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.service.JwtGenerator;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtGenerator1 jwtGenerator1;
    

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger bigInt = new BigInteger(1, messageDigest);
            String hashedPassword = bigInt.toString(16);
            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public Map<String, String> signupUser(AdminDetails admindata) {
        Map<String, String> response = new HashMap<>();
        String hashedPassword = hashPassword(admindata.getPassword());
        admindata.setPassword(hashedPassword);
       

        // Save the user
        AdminDetails savedUser = adminRepository.save(admindata);

        // Generate a token for the user
        Map<String, String> tokenResponse = jwtGenerator1.generateToken(savedUser);
        response.putAll(tokenResponse);

        return response;
    }

    public Map<String, String> loginUser(String username,String password) {
        Map<String, String> response = new HashMap<>();

        // Try to find the user by username
        AdminDetails adminuser = adminRepository.findByUsername(username);
        if (adminuser != null) {
            // Hash the provided password
            String hashedPassword = hashPassword(password);

            // Compare the hashed password with the stored password
            if (adminuser.getPassword().equals(hashedPassword)) {
                Map<String, String> tokenResponse = jwtGenerator1.generateToken(adminuser);
                response.putAll(tokenResponse);
            } else {
                response.put("message", "Invalid email or phone number, or password");
            }
        } else {
            response.put("message", "Invalid email or phone number, or password");
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
        AdminDetails adminuser = adminRepository.findByUsername(username);

        // Check if the user exists
        if (adminuser != null) {
            // Hash the new password
            String hashedPassword = hashPassword(newPassword);

            adminuser.setPassword(hashedPassword);
            AdminDetails updatedUser = adminRepository.save(adminuser);

            Map<String, String> tokenResponse = jwtGenerator1.generateToken(updatedUser);
            response.putAll(tokenResponse);
            response.put("message", "Your password has been updated successfully.");
        } else {
            response.put("message", "Please enter a valid email address.");
        }

        return response;
    }
}