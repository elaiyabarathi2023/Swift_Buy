package com.swiftbuy.CustomValidations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidators.class)
public @interface PasswordValidations {
  String message() default "Password Invalid";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  
}