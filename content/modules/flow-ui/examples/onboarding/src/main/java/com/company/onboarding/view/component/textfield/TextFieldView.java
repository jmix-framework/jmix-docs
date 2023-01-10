package com.company.onboarding.view.component.textfield;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.SupportsTypedValue;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "TextFieldView", layout = MainView.class)
@ViewController("TextFieldView")
@ViewDescriptor("text-field-view.xml")
public class TextFieldView extends StandardView {
    // tag::autowired-notifications[]
    @Autowired
    protected Notifications notifications;

    // end::autowired-notifications[]
    // tag::typedValueChangeEvent[]
    @Subscribe("nameField")
    protected void onNameFieldTypedValueChange(SupportsTypedValue.TypedValueChangeEvent<TypedTextField<String>, String> event) {
        notifications
                .show("Before: " + event.getOldValue() +
                        ". After: " + event.getValue());
    }
    // end::typedValueChangeEvent[]
    // tag::validator[]
    @Install(to = "zipField", subject = "validator")
    protected void zipFieldValidator(Integer value) {
        if (value != null && String.valueOf(value).length() != 6)
            throw new ValidationException("Zip must be of 6 digits length");
    }
    // end::validator[]
}