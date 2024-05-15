package com.swiftbuy.user.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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

    public Map<String, String> signupUser(UserDetails userdata) {
        Map<String, String> response = new HashMap<>();

        // Hash the password before saving the user
        String hashedPassword = hashPassword(userdata.getPassword());
        userdata.setPassword(hashedPassword);

        UserDetails savedUser = userRepository.save(userdata);

        Map<String, String> tokenResponse = jwtGenerator.generateToken(savedUser);
        response.putAll(tokenResponse);
        return response;
    }

    public Map<String, String> loginUser(String email, String phoneNumber, String password) {
        Map<String, String> response = new HashMap<>();

        UserDetails user = null;

        if (email != null && email.contains("@")) {
            user = userRepository.findByEmail(email);
        } else if (phoneNumber != null) {
            user = userRepository.findByPhoneNumber(phoneNumber);
        }

        if (user != null) {
            // Hash the provided password
            String hashedPassword = hashPassword(password);

            // Compare the hashed password with the stored password
            if (user.getPassword().equals(hashedPassword)) {
                Map<String, String> tokenResponse = jwtGenerator.generateToken(user);
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
        String email = requestData.get("email");
        String newPassword = requestData.get("password");

        if (email == null || email.isEmpty()) {
            response.put("message", "Please enter a valid email address.");
            return response;
        }

        UserDetails user = userRepository.findByEmail(email);

        if (user != null) {
            // Hash the new password
            String hashedPassword = hashPassword(newPassword);

            user.setPassword(hashedPassword);
            UserDetails updatedUser = userRepository.save(user);

            Map<String, String> tokenResponse = jwtGenerator.generateToken(updatedUser);
            response.putAll(tokenResponse);
            response.put("message", "Your password has been updated successfully.");
        } else {
            response.put("message", "Please enter a valid email address.");
        }

        return response;
    }
}