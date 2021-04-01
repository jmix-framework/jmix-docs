package ui.ex1.screen.actions;

import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.PopupButton;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@UiController("sample_ActionScreen")
@UiDescriptor("action-screen.xml")
public class ActionScreen extends Screen {
    // tag::say-btn-hello[]
    @Named("sayBtn.hello")
    private Action sayBtnHello;
    // end::say-btn-hello[]
    // tag::say-btn[]
    @Autowired
    private PopupButton sayBtn;
    // end::say-btn[]
    // tag::notifications[]
    @Autowired
    private Notifications notifications;
    // end::notifications[]

    @Subscribe("create")
    public void onCreate(Action.ActionPerformedEvent event) {
        notifications.create()
                .withCaption("Save called from " + event.getSource().getId())
                .show();
    }

    @Subscribe("remove")
    public void onRemove(Action.ActionPerformedEvent event) {
        notifications.create()
                .withCaption("Save called from " + event.getSource().getId())
                .show();
    }
    // tag::say-hello[]

    @Subscribe("sayHello")
    public void onSayHello(Action.ActionPerformedEvent event) {
        notifications.create()
                .withCaption("Hello")
                .withType(Notifications.NotificationType.HUMANIZED)
                .show();
    }
    // end::say-hello[]

    // tag::say-hello-goodbye[]

    private void showNotification(String message) {
        notifications.create()
                .withCaption(message)
                .withType(Notifications.NotificationType.HUMANIZED)
                .show();
    }

    @Subscribe("sayBtn.hello")
    public void onSayBtnHello(Action.ActionPerformedEvent event) {
        showNotification(event.getSource().getCaption());
    }

    @Subscribe("sayBtn.goodbye")
    public void onSayBtnGoodbye(Action.ActionPerformedEvent event) {
        showNotification(event.getSource().getCaption());
    }
    // end::say-hello-goodbye[]
    // tag::copy-action[]
    @Subscribe("customersTable.copy")
    public void onCustomersTableCopy(Action.ActionPerformedEvent event) {
        // ...
    }
    // end::copy-action[]
    // tag::before-show[]

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        sayBtnHello.setEnabled(false);
        sayBtn.getActionNN("goodbye").setEnabled(false);
    }
    // end::before-show[]
}