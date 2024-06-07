package com.swiftbuy.admin.service;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.swiftbuy.admin.exception.UsernameExistsException;
import com.swiftbuy.admin.model.AdminDetails;
import com.swiftbuy.admin.repository.AdminRepository;
@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepository;
//    public static String hashPassword(String password) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] messageDigest = md.digest(password.getBytes());
//            BigInteger bigInt = new BigInteger(1, messageDigest);
//            String hashedPassword = bigInt.toString(16);
//            return hashedPassword;
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error hashing password", e);
//        }
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
	public String testHashPassword(String password) throws NoSuchAlgorithmException {
		return hashPassword(password);
	}
	public Map<String, String> signupUser(AdminDetails userdata) throws NoSuchAlgorithmException {
		Map<String, String> response = new HashMap<>();
		String username = userdata.getUsername();
		String password = userdata.getPassword();
		// Check if username is empty
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be empty.");
		}
		// Check if password is empty
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("Password cannot be empty.");
		}
		// Check if email or username already exists
		if (adminRepository.existsByUsername(username)) {
			throw new UsernameExistsException("username already exists.");
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
			throw new Exception("Invalid Details Provided");
		}
	}
	public Map<String, String> forgotPassword(Map<String, String> requestData) {
		Map<String, String> response = new HashMap<>();
		response.put("message", "Password recovery not implemented.");
		return response;
	}
}