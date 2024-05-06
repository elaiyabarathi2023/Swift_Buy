package com.swiftbuy.CustomValidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    private static final String USERNAME_PATTERN = "^^[a-zA-Z]+$";

    @Override
    public boolean isValid(String firstname, ConstraintValidatorContext context) {
        if (firstname == null||firstname.isBlank()) {
            return false;
        }
        return firstname.matches(USERNAME_PATTERN);
    }
}