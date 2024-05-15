package com.swiftbuy.user.CustomValidations;







import java.util.regex.Pattern;

import com.swiftbuy.user.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(".*@.*");
    private UserRepository userRepository; // Reference to the repository

    // Constructor injection
    public EmailValidator(UserRepository userRepository) {
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
        boolean matchesPattern = EMAIL_PATTERN.matcher(value).matches();

        // Check if the email already exists in the database
        boolean existsInDatabase = userRepository.existsByEmail(value);

        // Return false if the email exists in the database or doesn't match the pattern
        return matchesPattern && !existsInDatabase;
    }
}