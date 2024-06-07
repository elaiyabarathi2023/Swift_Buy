package com.swiftbuy.user.CustomValidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidators implements ConstraintValidator<PasswordValidations, String> {

	@Override
	public void initialize(PasswordValidations constraintAnnotation) {

	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
	    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";

	    if (password == null || password.isEmpty()) {
	        return false;
	    }

	    return password.length() >= 8 && password.matches(regex);
	}


}
