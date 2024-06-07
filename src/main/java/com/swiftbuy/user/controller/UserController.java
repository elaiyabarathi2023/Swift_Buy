package com.swiftbuy.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.product.service.ProductService;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(path = "/signupuser")
	public ResponseEntity<Map<String, String>> SignupUser(@Valid @RequestBody UserDetails userdata) throws Exception {

		Map<String, String> createdUser;
		
			createdUser = userService.signupUser(userdata);
			return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
		
		
	}

	@PostMapping(path = "/loginuser")
	public ResponseEntity<Map<String, String>> loginUser(@Valid @RequestBody Map<String, String> body) {
	    String email = body.get("email");
	    String phoneNumber = body.get("phoneNumber");
	    String password = body.get("password");

	    Map<String, String> loggedInUser;
	    try {
	       
	            
	        

	        loggedInUser = userService.loginUser(email, phoneNumber, password);

	        

	        return new ResponseEntity<>(loggedInUser, HttpStatus.CREATED);
	    } catch (Exception e) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("status", "false");
	        errorResponse.put("message", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	}


	@PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> requestData) {
        Map<String, String> response = userService.forgotPassword(requestData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping

	public Iterable<UserDetails> getAllUsers() {
	    return userRepository.findAll();
	}
	
  


	
}



