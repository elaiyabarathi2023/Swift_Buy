package com.swiftbuy.admin.CustomValidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidatorsAdmin implements ConstraintValidator<PasswordValidationsAdmin, String> {

    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";

    @Override
    public void initialize(PasswordValidationsAdmin constraintAnnotation) {
        // No initialization required
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // Check for null or empty string
        if (password == null || password.isEmpty()) {
            return false;
        }

        // Check password length and regex pattern
        return password.length() >= 8 && password.matches(PASSWORD_REGEX);
    }
}