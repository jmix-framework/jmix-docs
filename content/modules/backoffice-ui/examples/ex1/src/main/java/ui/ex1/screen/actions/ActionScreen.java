package ui.ex1.screen.actions;

import io.jmix.core.Metadata;
import io.jmix.core.metamodel.model.MetaProperty;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.action.list.BulkEditAction;
import io.jmix.ui.action.list.CreateAction;
import io.jmix.ui.app.bulk.ColumnsMode;
import io.jmix.ui.bulk.BulkEditors;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.PopupButton;
import io.jmix.ui.screen.*;
import liquibase.pro.packaged.R;
import liquibase.pro.packaged.T;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.screen.entity.customer.CustomerEdit;

import javax.inject.Named;
import java.util.*;

@UiController("sample_ActionScreen")
@UiDescriptor("action-screen.xml")
public class ActionScreen extends Screen {
    // tag::for-bulk-action[]
    @Autowired
    private BulkEditors bulkEditors;
    @Autowired
    private Metadata metadata;
    @Autowired
    private GroupTable<Customer> custTable;

    // end::for-bulk-action[]
    @Autowired
    private Dialogs dialogs;

    // tag::bulk-action[]
    @Named("custTable.bulk")
    private BulkEditAction custTableBulk;

    // end::bulk-action[]
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
    // tag::table[]
    @Autowired
    private GroupTable<Customer> customersGroupTable;
    // end::table[]
    // tag::create-action[]
    @Named("customersGroupTable.create")
    private CreateAction<Customer> createAction;


    // end::create-action[]

    // tag::on-init-start[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::on-init-start[]
        // tag::create-action-set[]
        createAction.setOpenMode(OpenMode.DIALOG);
        createAction.setScreenClass(CustomerEdit.class);
        // end::create-action-set[]
        // tag::bulk-action-set[]
        custTableBulk.setOpenMode(OpenMode.THIS_TAB);
        custTableBulk.setIncludeProperties(Arrays.asList("rewardPoints", "email"));
        custTableBulk.setColumnsMode(ColumnsMode.ONE_COLUMN);
        // end::bulk-action-set[]
        // tag::on-init-end[]
    }
    // end::on-init-end[]

    // end::additional-properties[]
    // tag::handler[]
    @Install(to = "customersGroupTable.create", subject = "afterCommitHandler")
    private void customersGroupTableCreateAfterCommitHandler(Customer customer) {
        notifications.create()
                .withCaption("Created  " + customer)
                .show();
    }
    // end::handler[]
    // tag::enabledRule[]
    @Install(to = "customersGroupTable.remove", subject = "enabledRule")
    private boolean customersGroupTableRemoveEnabledRule() {
        Set<Customer> customers = customersGroupTable.getSelected();
        return canBeRemoved(customers);
    }
    // end::enabledRule[]
    private boolean canBeRemoved(Set<Customer> customers) {
        return false;
    }

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

    @Subscribe("sayHello") // <1>
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

    @Subscribe("sayBtn.hello") // <1>
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
    // tag::field-sorter[]
    @Install(to = "custTable.bulk", subject = "fieldSorter")
    private Map<MetaProperty, Integer> custTableBulkFieldSorter(List<MetaProperty> properties) {
        Map<MetaProperty, Integer> result = new HashMap<>();
        for (MetaProperty property : properties) {
            switch (property.getName()) {
                case "email": result.put(property, 0); break;
                case "rewardPoints": result.put(property, 1); break;
                default:
            }
        }
        return result;
    }
    // end::field-sorter[]
    // tag::bulk-action-performed-event[]
    @Subscribe("custTable.bulk")
    public void onCustTableBulk(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Are you sure you want to edit the selected entities?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> custTableBulk.execute()), // execute action
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::bulk-action-performed-event[]
    // tag::bulk-edit-action-performed-event[]
    @Subscribe("custTable.bulkEdit")
    public void onCustTableBulkEdit(Action.ActionPerformedEvent event) {
        bulkEditors.builder(metadata.getClass(Customer.class), custTable.getSelected(), this)
                .withListComponent(custTable)
                .withColumnsMode(ColumnsMode.ONE_COLUMN)
                .withIncludeProperties(Arrays.asList("rewardPoints", "email"))
                .create()
                .show();
    }
    // end::bulk-edit-action-performed-event[]
}