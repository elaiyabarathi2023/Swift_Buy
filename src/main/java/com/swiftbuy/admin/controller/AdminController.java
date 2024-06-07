package com.swiftbuy.admin.controller;
import com.swiftbuy.admin.exception.UsernameExistsException;
import com.swiftbuy.admin.model.AdminDetails;
import com.swiftbuy.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping(path = "/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@PostMapping("/signupuser")
	public ResponseEntity<Map<String, String>> signupUser(@RequestBody AdminDetails userdata) {
	    try {
	        Map<String, String> response = adminService.signupUser(userdata);
	        return ResponseEntity.ok(response);
	    } catch (UsernameExistsException e) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("message", e.getMessage());
	        return ResponseEntity.badRequest().body(errorResponse);
	    } catch (Exception e) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("message", e.getMessage());
	        return ResponseEntity.badRequest().body(errorResponse);
	    }
	}
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> requestBody) {
		String username = requestBody.get("username");
		String password = requestBody.get("password");
		try {
			Map<String, String> response = adminService.loginUser(username, password);
			Map<String, Object> customResponse = new HashMap<>();
			customResponse.put("message", "Login successful");
			customResponse.put("status", true);
			return ResponseEntity.ok(customResponse);
		} catch (Exception e) {
			// You can log the exception
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
		}
	}
	@PostMapping("/forgot-password")
	public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> requestData) {
		Map<String, String> response = adminService.forgotPassword(requestData);
		return ResponseEntity.ok(response);
	}
}