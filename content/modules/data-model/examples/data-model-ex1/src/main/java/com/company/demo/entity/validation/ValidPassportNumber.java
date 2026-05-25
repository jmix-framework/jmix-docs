package com.company.demo.entity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks that passport number is valid
 */
// tag::annotation[]
@Target(ElementType.TYPE) // <1>
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPassportNumberValidator.class) // <2>
public @interface ValidPassportNumber {
    String message() default "Passport number is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
// end::annotation[]