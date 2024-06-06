package com.swiftbuy.user.controller;

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
import com.swiftbuy.product.service.ProductService;
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
	@Autowired
	private ProductService productService;
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(path = "/signupuser")
	public ResponseEntity<Map<String, String>> SignupUser(@Valid @RequestBody UserDetails userdata) {

		Map<String, String> createdUser;
		try {
			createdUser = userService.signupUser(userdata);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
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
	            if (loggedInUser.containsKey("message") && "Invalid details provided!!!!".equals(loggedInUser.get("message"))) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loggedInUser);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	        }
	        return new ResponseEntity<>(loggedInUser, HttpStatus.CREATED);
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



