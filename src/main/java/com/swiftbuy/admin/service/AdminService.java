//package com.swiftbuy.admin.service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.swiftbuy.admin.model.AdminDetails;
//import com.swiftbuy.admin.repository.AdminRepository;
//
//@Service
//public class AdminService {
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private JwtGenerator1 jwtGenerator1;
//
//    public Map<String, String> signupUser(AdminDetails userdata) {
//        Map<String, String> response = new HashMap<>();
//
//       
//
//        // Save the user
//        AdminDetails savedUser = adminRepository.save(userdata);
//
//        // Generate a token for the user
//        Map<String, String> tokenResponse = jwtGenerator1.generateToken(savedUser);
//        response.putAll(tokenResponse);
//
//        return response;
//    }
//
//    public Map<String, String> loginUser(String username) {
//        Map<String, String> response = new HashMap<>();
//
//        // Try to find the user by username
//        AdminDetails user = adminRepository.findByUsername(username);
//
//        // Check if the user exists
//        if (user != null) {
//            // Generate a token for the user
//            Map<String, String> tokenResponse = jwtGenerator1.generateToken(user);
//            response.putAll(tokenResponse);
//        } else {
//            response.put("message", "Invalid username");
//        }
//
//        return response;
//    }
//    public Map<String, String> forgotPassword(Map<String, String> requestData) {
//        Map<String, String> response = new HashMap<>();
//        String username = requestData.get("username");
//        String newPassword = requestData.get("newPassword");
//
//        // Check if the email is null or empty
//        if (username == null || username.isEmpty()) {
//            response.put("message", "Please enter a valid email address.");
//            return response;
//        }
//
// AdminService.java
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

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

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

    public Map<String, String> signupUser(AdminDetails userdata) {
        Map<String, String> response = new HashMap<>();
      
        String username = userdata.getUsername();

        // Check if email or username already exists
        if ( adminRepository.existsByUsername(username)) {
            response.put("message", " username already exists.");
            return response;
        }

        String hashedPassword = hashPassword(userdata.getPassword());
        userdata.setPassword(hashedPassword);

        // Save the user
        adminRepository.save(userdata);

        response.put("message", "Admin signed up successfully.");
        return response;
    }
    public Map<String, String> loginUser(String username, String password) throws Exception {
        Map<String, String> response = new HashMap<>();
        AdminDetails user = adminRepository.findByUsername(username);
        if (user != null) {
            // Hash the provided password
            String hashedPassword = hashPassword(password);
            if (user.getPassword().equals(hashedPassword)) {
                response.put("message", "Login successful.");
                return response;
            } else {
                throw new Exception("Incorrect password.");
            }
        } else {
            throw new Exception("User not found.");
        }
    }

    public Map<String, String> forgotPassword(Map<String, String> requestData) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Password recovery not implemented.");
        return response;
    }
}

//        // Try to find the user by email
//        AdminDetails user = adminRepository.findByUsername(username);
//
//        // Check if the user exists
//        if (user != null) {
//            // Update the user's password
//            user.setPassword(newPassword);
//            AdminDetails updatedUser = adminRepository.save(user);
//
//            // Generate a new token for the user
//            Map<String, String> tokenResponse = jwtGenerator1.generateToken(updatedUser);
//
//            // Add the token to the response
//            response.putAll(tokenResponse);
//
//            // Add a success message to the response
//            response.put("message", "Your password has been updated successfully.");
//        } else {
//            // If the user is not found, return a message asking for a valid email
//            response.put("message", "Please enter a valid email address.");
//        }
//
//        return response;
//    }
//}