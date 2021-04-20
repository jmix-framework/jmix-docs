package ui.ex1.screen.component.table;


import io.jmix.email.EmailAttachment;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.ItemTrackingAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.screen.actions.SendByEmailAction;

import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

// tag::table-screen-start[]
@UiController("table-screen")
@UiDescriptor("table-screen.xml")
public class TableScreen extends Screen {

    // end::table-screen-start[]
    // tag::inject-customersTable[]
    @Autowired
    private Table<Customer> customersTable;
    // end::inject-customersTable[]
    // tag::inject-notifications[]
    @Autowired
    private Notifications notifications;

    // end::inject-notifications[]
    // tag::inject-SendByEmailAction[]
    @Named("customersTable.sendByEmail")// <1>
    private SendByEmailAction<Customer> customersTableSendByEmail;

    // end::inject-SendByEmailAction[]

    // tag::onInit-start[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit-start[]
        // tag::add-base-action[]
        customersTable.addAction(new AboutSingleAction());
        // end::add-base-action[]
        // tag::add-ItemTrackingAction[]
        customersTable.addAction(new ItemTrackingAction("about") {
            @Nullable
            @Override
            public String getCaption() {
                return "About";
            }

            @Override
            public void actionPerform(Component component) {
                notifications.create()
                        .withCaption("Hello " + customersTable.getSelected().iterator().next())
                        .withType(Notifications.NotificationType.TRAY)
                        .show();
            }
        });
        // end::add-ItemTrackingAction[]
        //customersTable.repaint();
        // tag::onInit-end[]
    }

    // end::onInit-end[]
    // tag::base-action-table[]
    private class AboutSingleAction extends BaseAction {

        public AboutSingleAction() {
            super("aboutSingle");
        }

        @Nullable
        @Override
        public String getCaption() {
            return "About Single";
        }

        @Override
        public void actionPerform(Component component) {
            notifications.create()
                    .withCaption("Hello " + customersTable.getSingleSelected())
                    .withType(Notifications.NotificationType.TRAY)
                    .show();
        }

        @Override
        public boolean isApplicable() {
            return customersTable != null && customersTable.getSelected().size() == 1;
        }
    }
    // end::base-action-table[]
    // tag::sendByEmailAction-handlers[]
    @Subscribe("customersTable.sendByEmail")
    public void onCustomersTableSendByEmailEmailSent(SendByEmailAction.EmailSentEvent event) {// <2>
        notifications.create(Notifications.NotificationType.HUMANIZED)
                .withCaption("Email sent")
                .show();
    }

    @Install(to = "customersTable.sendByEmail", subject = "bodyGenerator")
    private String customersTableSendByEmailBodyGenerator(Customer customer) {// <3>
        return "Hello, " + customer.getFirstName();
    }

    @Install(to = "customersTable.sendByEmail", subject = "attachmentProvider")
    private List<EmailAttachment> customersTableSendByEmailAttachmentProvider(Customer customer) {// <4>
        return Collections.emptyList();
    }
    // end::sendByEmailAction-handlers[]
    // tag::table-screen-end[]
}
// end::table-screen-end[]