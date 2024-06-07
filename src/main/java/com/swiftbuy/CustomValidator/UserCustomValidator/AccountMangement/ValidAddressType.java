package com.swiftbuy.CustomValidator.UserCustomValidator.AccountMangement;


import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = AddressTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAddressType {
    String message() default "Invalid address type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
