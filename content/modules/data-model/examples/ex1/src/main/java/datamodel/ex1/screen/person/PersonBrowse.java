package datamodel.ex1.screen.person;

import io.jmix.core.DataManager;
import io.jmix.ui.action.Action;
import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@UiController("sample_Person.browse")
@UiDescriptor("person-browse.xml")
@LookupComponent("personsTable")
public class PersonBrowse extends StandardLookup<Person> {
    // tag::validator[]
    @Autowired
    protected Validator validator;

    // end::validator[]
    @Autowired
    private DataManager dataManager;

    @Subscribe("personsTable.validate")
    public void onPersonsTableValidate(Action.ActionPerformedEvent event) {
        Person person = dataManager.create(Person.class);
        person.setLastName("George");
        person.setAge(25);
        person.setEmail("gfd@fds.ti");
        person.setFirstName("Alla");
        person.setPassportNumber("123321");
        save(person);
    }
    // tag::save[]
    protected void save(Person person) {
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        /*
        handling of the returned collection of violations
        */
    }
    // end::save[]
}