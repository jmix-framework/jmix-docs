package com.company.onboarding.view.dialogsandnotifications.dialogs;

import com.company.onboarding.entity.OnboardingStatus;
import com.company.onboarding.view.main.MainView;
import com.google.common.base.Strings;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.app.inputdialog.DialogActions;
import io.jmix.flowui.app.inputdialog.DialogOutcome;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import com.company.onboarding.entity.User;
import static io.jmix.flowui.app.inputdialog.InputParameter.*;

@Route(value = "DialogsSampleView", layout = MainView.class)
@ViewController("DialogsSampleView")
@ViewDescriptor("dialogs-sample-view.xml")
public class DialogsSampleView extends StandardView {
    // tag::inject-dialogs[]
    @Autowired
    private Dialogs dialogs;

    // end::inject-dialogs[]

    // tag::messageDialog-1[]
    @Subscribe("messageDialogButton")
    public void onHelloButtonClick(ClickEvent<Button> event) {
        dialogs.createMessageDialog()
                .withHeader("Success") // <1>
                .withText("Invitation sent successfully") // <2>
                .open();
    }
    // end::messageDialog-1[]

    // tag::messageDialog-custom[]
    @Subscribe("customDialogButton")
    public void onSanitizeButtonClick(ClickEvent<Button> event) {
        dialogs.createMessageDialog()
                .withHeader("Information")
                .withText("This is a custom dialog")
                .withCloseOnOutsideClick(true)
                .withWidth("600px")
                .withHeight("200px")
                .open();
    }
    // end::messageDialog-custom[]

    // tag::optionsDialog-1[]
    @Subscribe("selectOptionButton")
    public void onSelectOptionButtonClick(ClickEvent<Button> event) {
        dialogs.createOptionDialog()
                .withHeader("Please confirm")
                .withText("Do you really want to add a customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> addCustomer()), // <1>
                        new DialogAction(DialogAction.Type.NO)
                )
                .open();
    }
    // end::optionsDialog-1[]

    private void addCustomer() {

    }

//     tag::inputDialog-standard-parameters[]
    @Subscribe("standardParametersButton")
    public void onStandardParametersButtonClick(ClickEvent<Button> event) {
        dialogs.createInputDialog(this)
                .withHeader("Enter values")
                .withParameters(
                        stringParameter("name").withLabel("Name").withRequired(true), // <1>
                        intParameter("amount").withLabel("Amount").withDefaultValue(1), // <2>
                        entityParameter("user", User.class).withLabel("User"), // <3>
                        enumParameter("status", OnboardingStatus.class).withLabel("Status") // <4>
                )
                .withActions(DialogActions.OK_CANCEL) // <5>
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) { // <6>
                        String name = closeEvent.getValue("name"); // <7>
                        int amount = closeEvent.getValue("amount");
                        User user = closeEvent.getValue("user");
                        OnboardingStatus status = closeEvent.getValue("status");
                        // process entered values...
                    }
                })
                .open();

    }
    // end::inputDialog-standard-parameters[]

    // tag::inputDialog-custom-parameters[]
    @Autowired
    private DataManager dataManager;
    @Autowired
    private UiComponents uiComponents;

    @Subscribe("customParameterButton")
    public void onCustomParameterButtonClick(ClickEvent<Button> event) {
        dialogs.createInputDialog(this)
                .withHeader("Enter values")
                .withParameters(
                        stringParameter("name").withLabel("Name").withRequired(true),
                        intParameter("amount").withLabel("Amount").withDefaultValue(1),
                        parameter("user") // <1>
                                .withLabel("User")
                                .withField(() -> {
                                    EntityComboBox<User> field = uiComponents.create(EntityComboBox.class); // <2>
                                    field.setItems(dataManager.load(User.class).all().list()); // <3>
                                    field.setWidthFull();
                                    return field;
                                }),
                        enumParameter("status", OnboardingStatus.class).withLabel("Status")
                )
                .withActions(DialogActions.OK_CANCEL).withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        String name = closeEvent.getValue("name");
                        int amount = closeEvent.getValue("amount");
                        User user = closeEvent.getValue("user");
                        OnboardingStatus status = closeEvent.getValue("status");
                        // process entered values...
                    }
                })
                .open();
    }
    // end::inputDialog-custom-parameters[]


    // tag::inputDialog-custom-validator[]
    @Subscribe("validationButton")
    public void onValidationButtonClick(ClickEvent<Button> event) {
        dialogs.createInputDialog(this)
                .withHeader("Enter at least one value")
                .withParameters(
                        stringParameter("name").withLabel("Name").withRequired(true),
                        entityParameter("User", User.class).withLabel("User")
                )
                .withValidator(context -> { // <1>
                    String name = context.getValue("name"); // <2>
                    User user = context.getValue("user");
                    if (Strings.isNullOrEmpty(name) && user == null) {
                        return ValidationErrors.of("Enter name or select a customer"); // <3>
                    }
                    return ValidationErrors.none();
                })
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        String name = closeEvent.getValue("name");
                        User user = closeEvent.getValue("user");
                        // process entered values...
                    }
                })
                .open();
    }
    // end::inputDialog-custom-validator[]
}