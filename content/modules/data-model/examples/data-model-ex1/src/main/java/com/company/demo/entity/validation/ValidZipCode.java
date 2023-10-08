package com.company.demo.entity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// tag::annotation[]
@NotNull
@Size(min = 2, max = 14)
@Pattern(regexp = "\\d+")
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
public @interface ValidZipCode {
    String message() default "Zip code is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
// end::annotation[]