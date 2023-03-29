package com.company.onboarding.view.passwordfield;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textfield.JmixPasswordField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "PasswordFieldView", layout = MainView.class)
@ViewController("PasswordFieldView")
@ViewDescriptor("password-field-view.xml")
public class PasswordFieldView extends StandardView {
    // tag::basics[]
    @ViewComponent
    protected JmixPasswordField passwordField;
    @Autowired
    protected Notifications notifications;

    @Subscribe("createPasswordButton")
    protected void onButtonClick(ClickEvent<Button> event) {
        if (passwordField.getValue().isEmpty() == false)
            notifications.create("Password created")
                    .show();
        }
    // end::basics[]
}
