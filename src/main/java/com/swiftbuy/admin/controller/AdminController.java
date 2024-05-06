package com.swiftbuy.admin.controller;

import com.swiftbuy.admin.model.AdminDetails;
import com.swiftbuy.admin.service.AdminService;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.service.UserService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@PostMapping(path = "/signupuser")
	public ResponseEntity<Map<String, String>> SignupUser(@Valid @RequestBody AdminDetails userdata) {

		Map<String, String> createdUser;
		try {
			createdUser = adminService.signupUser(userdata);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}


	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> body) {
	    String username = body.get("username");
	    Map<String, String> response = adminService.loginUser(username);
	    return ResponseEntity.ok(response);
	}

	@PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> requestData) {
        Map<String, String> response = adminService.forgotPassword(requestData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping

	public Iterable<UserDetails> getAllUsers() {
	    return userRepository.findAll();
	}
}