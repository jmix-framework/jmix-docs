package ui.ex1.screen.screens.validate;

import ui.ex1.entity.Event;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// tag::event-date-validator[]
public class EventDateValidator implements ConstraintValidator<EventDate, Event> {

    @Override
    public boolean isValid(Event event, ConstraintValidatorContext context) {
        if (event == null) {
            return false;
        }

        if (event.getStartDate() == null || event.getEndDate() == null) {
            return false;
        }

        return event.getStartDate().before(event.getEndDate());
    }
}
// end::event-date-validator[]
