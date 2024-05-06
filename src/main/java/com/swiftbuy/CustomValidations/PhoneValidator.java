package com.swiftbuy.CustomValidations;

import java.util.regex.Pattern;

import com.swiftbuy.user.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
	 private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\d{10}$");
	 private UserRepository userRepository; // Reference to the repository

	    // Constructor injection
	    public PhoneValidator(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }

	    @Override
	    public boolean isValid(String value, ConstraintValidatorContext context) {
	        // Check for null or empty string
	        if (value == null || value.trim().isEmpty()) {
	            return false;
	        }

	        boolean matchesPattern = PHONE_NUMBER_PATTERN.matcher(value).matches();
	        boolean existsInDatabase = userRepository.existsByPhoneNumber(value);
	        return matchesPattern && !existsInDatabase;
	    }
	}