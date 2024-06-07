
package com.swiftbuy.user.CustomValidations;

import java.util.regex.Pattern;

import com.swiftbuy.user.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhone, String> {

	private static Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\d{10}$");

	private UserRepository userRepository;

	public PhoneNumberValidator(UserRepository userRepository) {

		this.userRepository = userRepository;

	}

	@Override

	public boolean isValid(String value, ConstraintValidatorContext context) {

		// Check for null or empty string

		if (value == null || value.trim().isEmpty()) {

			return false;

		}

		// Check if the phone number matches the pattern

		boolean matchesPattern = PHONE_NUMBER_PATTERN.matcher(value).matches();

		// Check if the email already exists in the database

		boolean existsInDatabase = userRepository.existsByPhoneNumber(value);

		// Return false if the email exists in the database or doesn't match the pattern

		return matchesPattern && !existsInDatabase;

	}

}