package com.swiftbuy.CustomValidations;

import java.util.regex.Pattern;

import com.swiftbuy.user.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

 

public class PhoneNumberValidator implements ConstraintValidator<ValidPhone, String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[2-9]{2}\\d{8}$");
    private UserRepository userRepository; // Reference to the repository

    // Constructor injection
    public PhoneNumberValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Or, setter method injection
    // public void setUserRepository(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Check for null or empty string
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        // Check if the email matches the pattern
        boolean matchesPattern = PHONE_PATTERN.matcher(value).matches();

        // Check if the email already exists in the database
        boolean existsInDatabase = userRepository.existsByPhoneNumber(value);

        // Return false if the email exists in the database or doesn't match the pattern
        return matchesPattern && !existsInDatabase;
    }
}