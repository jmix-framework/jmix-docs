package ui.ex1.screen.component.checkbox;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.ValidationException;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("CheckBoxScreen")
@UiDescriptor("check-box-screen.xml")
public class CheckBoxScreen extends Screen {
    @Autowired
    protected CheckBox checkBoxValid;
    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    // tag::value-change-event[]
    @Subscribe("checkBox")
    protected void onCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if (Boolean.TRUE.equals(event.getValue())) {
            notifications.create()
                    .withCaption("set")
                    .show();
        } else {
            notifications.create()
                    .withCaption("not set")
                    .show();
        }
    }
    // end::value-change-event[]
    // tag::validator[]
    @Install(to = "checkBoxValid", subject = "validator")
    protected void checkBoxValidValidator(Boolean value) {
        if (!value)
            throw new ValidationException("You must accept the terms of the license");
    }
    // end::validator[]

    @Subscribe("validBtn")
    protected void onValidBtnClick(Button.ClickEvent event) {
        checkBoxValid.validate();
    }
}