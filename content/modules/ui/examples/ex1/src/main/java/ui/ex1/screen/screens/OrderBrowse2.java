package ui.ex1.screen.screens;

import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.navigation.UrlParamsChangedEvent;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Order;

import java.util.Map;

@UiController("uiex1_Order2.browse")
@UiDescriptor("order-browse2.xml")
@LookupComponent("table")
public class OrderBrowse2 extends MasterDetailScreen<Order> {
    @Autowired
    private Notifications notifications;

    // tag::before-commit[]
    @Autowired
    private Dialogs dialogs;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (getEditedEntity().getNum() == null) {
            dialogs.createOptionDialog()
                    .withCaption("Confirmation")
                    .withMessage("Number is empty. Do you want to commit?")
                    .withActions(
                            new DialogAction(DialogAction.Type.OK).withHandler(e -> {
                                event.resume();
                            }),
                            new DialogAction(DialogAction.Type.CANCEL)
                    )
                    .show();
            event.preventCommit();
        }
    }
    // end::before-commit[]

    // tag::url-params[]
    @Subscribe
    protected void onUrlParamsChanged(UrlParamsChangedEvent event) {
        Map<String, String> params = event.getParams();
        notifications.create().withCaption("Params").show();
        // handle new params
    }
    // end::url-params[]
}