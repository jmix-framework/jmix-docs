package ui.ex1.screen.screens;

import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.component.CloseOriginType;
import io.jmix.ui.component.Window;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Order;

@UiController("uiex1_Order3.edit")
@UiDescriptor("order-edit3.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit3 extends StandardEditor<Order> {

    private static final String DEFAULT_NUM = "01";

    // tag::after-close1[]
    @Autowired
    private Notifications notifications;

    // end::after-close1[]

    // tag::before-commit[]
    @Autowired
    private Dialogs dialogs;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        if (getEditedEntity().getNum() == null) {
            dialogs.createOptionDialog()
                    .withCaption("Confirmation")
                    .withMessage("Number is empty. Do you want to set default?")
                    .withActions(
                            new DialogAction(DialogAction.Type.OK).withHandler(e -> {
                                getEditedEntity().setNum(DEFAULT_NUM);
                                event.resume(commitChanges());
                            }),
                            new DialogAction(DialogAction.Type.CANCEL)
                    )
                    .show();
            event.preventCommit();
        }
    }
    // end::before-commit[]

    // tag::before-close[]
    @Subscribe(target = Target.FRAME)
    protected void onBeforeClose(Window.BeforeCloseEvent event) {
        if (event.getCloseOrigin() == CloseOriginType.BREADCRUMBS) {
            event.preventWindowClose();
        }
    }
    // end::before-close[]

    // tag::after-close2[]
    @Subscribe
    protected void onAfterClose(AfterCloseEvent event) {
        notifications.create().withCaption("Just closed").show();
    }
    // end::after-close2[]
}