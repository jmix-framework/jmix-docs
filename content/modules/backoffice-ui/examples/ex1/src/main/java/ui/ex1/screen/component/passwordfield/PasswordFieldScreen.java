package ui.ex1.screen.component.passwordfield;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("PasswordFieldScreen")
@UiDescriptor("password-field-screen.xml")
public class PasswordFieldScreen extends Screen {
    @Autowired
    protected PasswordField passwordValidField;
    // tag::password-text-field[]
    @Autowired
    private PasswordField passwordField;
    @Autowired
    private Notifications notifications;

    @Subscribe("showPasswordBtn")
    protected void onShowPasswordBtnClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption(passwordField.getValue())
                .show();
    }
    // end::password-text-field[]

    @Subscribe("validateBtn")
    protected void onValidateBtnClick(Button.ClickEvent event) {
        passwordValidField.validate();
    }
}