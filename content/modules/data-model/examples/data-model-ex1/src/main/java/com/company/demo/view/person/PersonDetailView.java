package com.company.demo.view.person;

import com.company.demo.entity.Person;

import com.company.demo.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.validation.group.UiComponentChecks;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.validation.bean.BeanPropertyValidator;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

// tag::person-edit-start[]
@Route(value = "persons/:id", layout = MainView.class)
@ViewController("Person.detail")
@ViewDescriptor("person-detail-view.xml")
@EditedEntityContainer("personDc")
public class PersonDetailView extends StandardDetailView<Person> {
    // end::person-edit-start[]
    // tag::set-or-remove[]
    @ViewComponent
    private TypedTextField<String> passportNumberField;

//    @Subscribe("removeValidBtn")
//    public void onRemoveValidBtnClick(final ClickEvent<JmixButton> event) {
//        Collection<? extends Validator<?>> validators =
//                passportNumberField.getValidators();
//
//        for (Validator validator : validators.toArray(new Validator[0])) {
//            if (validator instanceof BeanPropertyValidator) {
//                passportNumberField.removeValidator(validator); // <1>
//            }
//        }
//    }
//
//    @Subscribe("setValidGroupBtn")
//    public void onSetValidGroupBtnClick(final ClickEvent<JmixButton> event) {
//        Collection<? extends Validator<?>> validators =
//                passportNumberField.getValidators();
//
//        for (Validator validator : validators.toArray(new Validator[0])) {
//            if (validator instanceof BeanPropertyValidator) {
//                ((BeanPropertyValidator) validator).setValidationGroups(
//                        new Class[] {UiComponentChecks.class}); // <2>
//            }
//        }
//    }
    // end::set-or-remove[]

    // tag::set-cross-field-validate[]
    @Subscribe("cancelCrossFValidBtn")
    public void onCancelCrossFValidBtnClick(final ClickEvent<JmixButton> event) {
        setCrossFieldValidationEnabled(false);
    }
    // end::set-cross-field-validate[]
    // tag::person-edit-end[]
}
// end::person-edit-end[]