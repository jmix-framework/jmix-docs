package com.company.demo.view.person;

import com.company.demo.entity.Person;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Route(value = "persons", layout = MainView.class)
@ViewController("Person.list")
@ViewDescriptor("person-list-view.xml")
@LookupComponent("personsDataGrid")
@DialogMode(width = "64em")
public class PersonListView extends StandardListView<Person> {
    // tag::validator[]
    @Autowired
    private Validator validator;

    // end::validator[]

    @Autowired
    private DataManager dataManager;

    @Subscribe("personsDataGrid.validate")
    public void onPersonsDataGridValidate(final ActionPerformedEvent event) {
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
        // handle collection of violations
    }
    // end::save[]
}