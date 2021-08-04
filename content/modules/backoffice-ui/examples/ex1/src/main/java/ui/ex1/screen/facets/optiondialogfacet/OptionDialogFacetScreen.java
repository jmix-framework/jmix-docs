package ui.ex1.screen.facets.optiondialogfacet;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.ActionsAwareDialogFacet;
import io.jmix.ui.component.OptionDialogFacet;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_OptionDialogFacetScreen")
@UiDescriptor("option-dialog-facet-screen.xml")
public class OptionDialogFacetScreen extends Screen {
    // tag::dialog-action-performed-event[]
    @Autowired
    protected Notifications notifications;

    @Install(to = "optionDialog.ok", subject = "actionHandler")
    protected void onDialogOkAction(ActionsAwareDialogFacet.DialogActionPerformedEvent<OptionDialogFacet> event) {
        String actionId = event.getDialogAction().getId();
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption("Dialog action performed: " + actionId)
                .show();
    }
    // end::dialog-action-performed-event[]
}