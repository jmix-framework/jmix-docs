package ui.ex1.screen.screens.validate;

import com.vaadin.shared.ui.dnd.criteria.Payload;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// tag::event-date-annotation[]
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventDateValidator.class)
public @interface EventDate {

    String message() default "Start date must be earlier than the end date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
// end::event-date-annotation[]
