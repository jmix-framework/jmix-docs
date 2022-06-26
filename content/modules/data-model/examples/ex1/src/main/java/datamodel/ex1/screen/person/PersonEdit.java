package datamodel.ex1.screen.person;

import io.jmix.core.validation.group.UiComponentChecks;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.validation.Validator;
import io.jmix.ui.component.validator.BeanPropertyValidator;
import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

// tag::person-edit-start[]
@UiController("sample_Person.edit")
@UiDescriptor("person-edit.xml")
@EditedEntityContainer("personDc")
public class PersonEdit extends StandardEditor<Person> {

    // end::person-edit-start[]
    // tag::set-or-remove[]
    @Autowired
    private TextField<String> passportNumberField;

    @Subscribe("removeValidator")
    public void onRemoveValidator(Action.ActionPerformedEvent event) {
        Collection<? extends Validator<?>> validators =
                passportNumberField.getValidators();

        for (Validator validator : validators.toArray(new Validator[0])) {
            if (validator instanceof BeanPropertyValidator) {
                passportNumberField.removeValidator(validator); // <1>
            }
        }
    }

    @Subscribe("setValidGroups")
    public void onSetValidGroups(Action.ActionPerformedEvent event) {
        Collection<? extends Validator<?>> validators =
                passportNumberField.getValidators();

        for (Validator validator : validators.toArray(new Validator[0])) {
            if (validator instanceof BeanPropertyValidator) {
                ((BeanPropertyValidator) validator).setValidationGroups(
                        new Class[] {UiComponentChecks.class}); // <2>
            }
        }
    }
    // end::set-or-remove[]
    // tag::set-cross-field-validate[]
    @Subscribe("cancelCrossFValidate")
    public void onCancelCrossFValidate(Action.ActionPerformedEvent event) {
        setCrossFieldValidate(false);
    }
    // end::set-cross-field-validate[]
    // tag::person-edit-end[]
}
// end::person-edit-end[]