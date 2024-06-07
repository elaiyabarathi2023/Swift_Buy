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
	private String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = md.digest(password.getBytes());
		BigInteger number = new BigInteger(1, hashBytes);
		StringBuilder hexString = new StringBuilder(number.toString(16));
//        while (hexString.length() < 32) {
//            hexString.insert(0, '0');
//        }
		return hexString.toString();
	}
 
	public Map<String, String> signupUser(UserDetails userdata) throws Exception {
		Map<String, String> response = new HashMap<>();
 
		   String hashedPassword = hashPassword(userdata.getPassword());
		    userdata.setPassword(hashedPassword);
		UserDetails savedUser = userRepository.save(userdata);
 
		// Generate a token for the user
		Map<String, String> tokenResponse = jwtGenerator.generateToken(savedUser);
		response.putAll(tokenResponse);
 
		return response;
	}
	public Map<String, String> loginUser(String email, String phoneNumber, String password) throws Exception {
	    Map<String, String> response = new HashMap<>();
 
	    // Check if both email and phone number are provided
	    if ((email != null && !email.isEmpty()) && (phoneNumber != null && !phoneNumber.isEmpty())) {
	        throw new Exception("Please provide either email or phone number, not both.");
	    }
 
	    // Initialize user as null
	    UserDetails user = null;
 
	    // Try to find the user by email or phone number
	    if (email != null && email.contains("@")) {
	        user = userRepository.findByEmail(email);
	    } else if (phoneNumber != null) {
	        user = userRepository.findByPhoneNumber(phoneNumber);
	    }
	    String hashedPassword = hashPassword(password);
	    // Check if the user exists and the password matches
	    if (user != null && user.getPassword().equals(hashPassword(password))) {
	        // Generate a token for the user
	        Map<String, String> tokenResponse = jwtGenerator.generateToken(user);
	        response.putAll(tokenResponse);
	    } else {
	        // If user is not found or password does not match, return an error message
	        throw new Exception("Invalid details provided!!!!");
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