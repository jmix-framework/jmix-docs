package datamodel.ex1.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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