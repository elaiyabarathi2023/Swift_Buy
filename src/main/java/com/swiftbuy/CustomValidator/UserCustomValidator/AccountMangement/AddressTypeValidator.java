package com.swiftbuy.CustomValidator.UserCustomValidator.AccountMangement;
//
//public class AddressTypeValidator {
//
//}



import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressTypeValidator implements ConstraintValidator<ValidAddressType, String> {
    List<String> validTypes = Arrays.asList("home", "work", "others");

    @Override
    public void initialize(ValidAddressType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && validTypes.contains(value.toLowerCase());
    }
}
