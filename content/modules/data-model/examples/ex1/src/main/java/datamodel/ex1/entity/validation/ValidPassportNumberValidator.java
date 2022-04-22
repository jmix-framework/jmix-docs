package datamodel.ex1.entity.validation;

import datamodel.ex1.entity.Location;
import datamodel.ex1.entity.Person;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// tag::validator[]
public class ValidPassportNumberValidator
        implements ConstraintValidator<ValidPassportNumber, Person> {

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext context) { // <1>
        if (person == null)
            return false;

        if (person.getLocation() == null || person.getPassportNumber() == null)
            return false;

        return doPassportNumberFormatCheck(person.getLocation(),
                person.getPassportNumber());
    }
    // end::validator[]
    private boolean doPassportNumberFormatCheck(Location location, String passportNumber) {
        // dumb check that ensures that passport number is not empty and
        // contains only digits and spaces after trimming trailing and leading spaces
        Pattern pat = Pattern.compile("(^[\\d\\s]+$)", Pattern.CASE_INSENSITIVE);
        Matcher mat = pat.matcher(passportNumber.trim());
        return (passportNumber.trim().length() > 0) && mat.find();
    }
    // tag::validator[]
}
// end::validator[]