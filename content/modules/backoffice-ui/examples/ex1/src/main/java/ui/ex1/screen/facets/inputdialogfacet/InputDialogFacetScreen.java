package ui.ex1.screen.facets.inputdialogfacet;

import io.jmix.ui.Notifications;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.inputdialog.InputDialog;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.screen.*;
import jdk.internal.joptsimple.internal.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@UiController("sample_InputDialogFacetScreen")
@UiDescriptor("input-dialog-facet-screen.xml")
public class InputDialogFacetScreen extends Screen {
    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    // tag::dialog-result-handler[]
    @Install(to = "inputDialog", subject = "dialogResultHandler")
    private void inputDialogDialogResultHandler(InputDialog.InputDialogResult inputDialogResult) {
        String closeActionType = inputDialogResult.getCloseActionType().name();

        String values = inputDialogResult.getValues().entrySet()
                .stream()
                .map(entry -> String.format("%s = %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));

        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption("InputDialog Result Handler")
                .withDescription("Close Action: " + closeActionType +
                        ". Values: " + values)
                .show();
    }
    // end::dialog-result-handler[]

    // tag::validator[]
    @Install(to = "inputDialog", subject = "validator")
    private ValidationErrors inputDialogValidator(InputDialog.ValidationContext validationContext) {
        String phone = validationContext.getValue("phoneField");
        String address = validationContext.getValue("addressField");
        if (Strings.isNullOrEmpty(phone) && Strings.isNullOrEmpty(address)) {
            return ValidationErrors.of("Phone or Address must be filled");
        }
        return ValidationErrors.none();
    }
    // end::validator[]

    // tag::input-dialog-close-event[]
    @Subscribe("inputDialog")
    public void onInputDialogInputDialogClose(InputDialog.InputDialogCloseEvent event) {
        if (event.closedWith(DialogOutcome.OK)) {
            notifications.create()
                    .withCaption("The order will display in your profile within 5 minutes")
                    .show();
        }
    }
    // end::input-dialog-close-event[]
}
