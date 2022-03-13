package ui.ex1.screen.actions;

import io.jmix.core.Metadata;
import io.jmix.core.common.util.ParamsMap;
import io.jmix.core.metamodel.model.MetaProperty;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.RemoveOperation;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.action.list.*;
import io.jmix.ui.action.tagpicker.TagLookupAction;
import io.jmix.ui.app.bulk.ColumnsMode;
import io.jmix.ui.bulk.BulkEditors;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.screen.entity.customer.CustomerBrowse;
import ui.ex1.screen.entity.customer.CustomerEdit;

import javax.inject.Named;
import java.util.*;

@UiController("action-screen")
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
    // tag::cust-table-create[]
    @Named("custTable.create")
    private CreateAction<Customer> custTableCreate;

    // end::cust-table-create[]
    // tag::inject-screen-builders[]
    @Autowired
    private ScreenBuilders screenBuilders;

    // end::inject-screen-builders[]
    // tag::injection-for-actions-performed[]
    @Autowired
    private GroupTable<Customer> customersTable;

    // end::injection-for-actions-performed[]
    // tag::inject-edit-action[]
    @Named("customersGroupTable.edit")
    private EditAction<Customer> editAction;

    // end::inject-edit-action[]
    // tag::inject-table-edit-action[]
    @Named("custTable.edit")
    private EditAction<Customer> custTableEdit;

    // end::inject-table-edit-action[]
    // tag::inject-table-refresh-action[]
    @Named("custTable.refresh")
    private RefreshAction refreshAction;

    // end::inject-table-refresh-action[]
    // tag::inject-customersDl[]
    @Autowired
    private CollectionLoader<Customer> customersDl;

    // end::inject-customersDl[]

    // tag::inject-table-remove-action[]
    @Named("custTable.remove")
    private RemoveAction<Customer> removeAction;

    // end::inject-table-remove-action[]
    // tag::inject-remove-operation[]
    @Autowired
    private RemoveOperation removeOperation;
    // end::inject-remove-operation[]

    // tag::inject-view-action[]
    @Named("custTable.view")
    private ViewAction<Customer> viewAction;

    // end::inject-view-action[]

    // tag::inject-tagLookupAction[]
    @Named("customerTagPicker.tagLookup")
    private TagLookupAction<Customer> tagLookupAction;

    // end::inject-tagLookupAction[]

    // tag::inject-customerTagPicker[]
    @Autowired
    private TagPicker<Customer> customerTagPicker;

    // end::inject-customerTagPicker[]
    // tag::inject-sayHelloBtn[]
    @Autowired
    private Button sayHelloBtn;
    @Autowired
    private Button sayGoodbyeBtn;

    // end::inject-sayHelloBtn[]

    // tag::on-init-start[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::on-init-start[]
        // tag::create-action-set[]
        createAction.setOpenMode(OpenMode.DIALOG);
        createAction.setScreenClass(CustomerEdit.class);
        // end::create-action-set[]
        // tag::edit-action-set[]
        editAction.setOpenMode(OpenMode.DIALOG);
        editAction.setScreenClass(CustomerEdit.class);
        // end::edit-action-set[]
        // tag::bulk-action-set[]
        custTableBulk.setOpenMode(OpenMode.THIS_TAB);
        custTableBulk.setIncludeProperties(Arrays.asList("rewardPoints", "email"));
        custTableBulk.setColumnsMode(ColumnsMode.ONE_COLUMN);
        // end::bulk-action-set[]
        // tag::remove-action-set[]
        removeAction.setConfirmation(true);
        removeAction.setConfirmationTitle("Removing customer...");
        removeAction.setConfirmationMessage("Do you really want to remove the customer?");
        // end::remove-action-set[]
        // tag::view-action-set[]
        viewAction.setOpenMode(OpenMode.DIALOG);
        viewAction.setScreenClass(CustomerEdit.class);
        // end::view-action-set[]
        // tag::tagLookup-action-set[]
        tagLookupAction.setOpenMode(OpenMode.DIALOG);
        tagLookupAction.setScreenClass(CustomerBrowse.class);
        // end::tagLookup-action-set[]
        // tag::base-action-button[]
        sayHelloBtn.setAction(new BaseAction("hello") {
            @Override
            public boolean isPrimary() {
                return true;
            }

            @Override
            public void actionPerform(Component component) {
                notifications.create()
                        .withCaption("Hello!")
                        .withType(Notifications.NotificationType.TRAY)
                        .show();
            }
        });

        sayGoodbyeBtn.setAction(new BaseAction("goodbye")
                .withPrimary(true)
                .withHandler(e ->
                        notifications.create()
                                .withCaption("Goodbye!")
                                .withType(Notifications.NotificationType.TRAY)
                                .show()));
        // end::base-action-button[]
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
    // tag::screen-options-supplier[]
    @Install(to = "custTable.create", subject = "screenOptionsSupplier")
    private ScreenOptions custTableCreateScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("someParameter", 10));
    }
    // end::screen-options-supplier[]
    // tag::screen-configurer[]
    @Install(to = "custTable.create", subject = "screenConfigurer")
    private void custTableCreateScreenConfigurer(Screen screen) {
        ((CustomerEdit) screen).setSomeParameter(10);
    }
    // end::screen-configurer[]
    // tag::new-entity-supplier[]
    @Install(to = "custTable.create", subject = "newEntitySupplier")
    private Customer custTableCreateNewEntitySupplier() {
        Customer customer = metadata.create(Customer.class);
        customer.setFirstName("Sean");
        customer.setLastName("White");
        return customer;
    }
    // end::new-entity-supplier[]
    // tag::initializer[]
    @Install(to = "custTable.create", subject = "initializer")
    private void custTableCreateInitializer(Customer customer) {
        customer.setFirstName("Abel");
        customer.setLastName("Higgins");
    }
    // end::initializer[]

    // tag::after-commit-handler[]
    @Install(to = "custTable.create", subject = "afterCommitHandler")
    private void custTableCreateAfterCommitHandler(Customer customer) {
        System.out.println("Created " + customer);
    }
    // end::after-commit-handler[]
    // tag::after-close-handler[]
    @Install(to = "custTable.create", subject = "afterCloseHandler")
    private void custTableCreateAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
            System.out.println("Committed");
        }
    }
    // end::after-close-handler[]

    // tag::transformation[]
    @Install(to = "custTable.create", subject = "transformation")
    private Customer custTableCreateTransformation(Customer customer) {
        return reloadCustomer(customer);
    }
    // end::transformation[]

    private Customer reloadCustomer(Customer customer){
        return customer;
    }
    // tag::action-performed-event[]
    @Subscribe("custTable.create")
    public void onCustTableCreate(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Do you really want to create new customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> custTableCreate.execute()), // execute action
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::action-performed-event[]
    // tag::action-performed-event-2[]
    @Subscribe("customersTable.create")
    public void onCustomersTableCreate(Action.ActionPerformedEvent event) {
        screenBuilders.editor(customersTable)
                .newEntity()
                .withOpenMode(OpenMode.DIALOG)
                .withScreenClass(CustomerEdit.class)
                .withAfterCloseListener(afterScreenCloseEvent -> {
                    if (afterScreenCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        Customer committedCustomer = (afterScreenCloseEvent.getSource()).getEditedEntity();
                        System.out.println("Created " + committedCustomer);
                    }
                })
                .build()
                .show();
    }
    // end::action-performed-event-2[]

    // tag::edit-screen-options-supplier[]
    @Install(to = "custTable.edit", subject = "screenOptionsSupplier")
    private ScreenOptions custTableEditScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("someParameter", 10));
    }
    // end::edit-screen-options-supplier[]
    // tag::edit-screen-configurer[]
    @Install(to = "custTable.edit", subject = "screenConfigurer")
    private void custTableEditScreenConfigurer(Screen screen) {
        ((CustomerEdit) screen).setSomeParameter(10);
    }
    // end::edit-screen-configurer[]
    // tag::edit-transformation[]
    @Install(to = "custTable.edit", subject = "transformation")
    private Customer custTableEditTransformation(Customer customer) {
        return reloadCustomer(customer);
    }
    // end::edit-transformation[]
    // tag::edit-after-commit-handler[]
    @Install(to = "custTable.edit", subject = "afterCommitHandler")
    private void custTableEditAfterCommitHandler(Customer customer) {
        System.out.println("Updated " + customer);
    }
    // end::edit-after-commit-handler[]
    // tag::edit-after-close-handler[]
    @Install(to = "custTable.edit", subject = "afterCloseHandler")
    private void custTableEditAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
            System.out.println("Committed");
        }
    }
    // end::edit-after-close-handler[]
    // tag::edit-action-performed-event[]
    @Subscribe("custTable.edit")
    public void onCustTableEdit(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Do you really want to edit the customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> custTableEdit.execute()), // execute action
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::edit-action-performed-event[]
    // tag::edit-action-performed-event-2[]
    @Subscribe("customersTable.edit")
    public void onCustomersTableEdit(Action.ActionPerformedEvent event) {
        screenBuilders.editor(customersTable)
                .withOpenMode(OpenMode.DIALOG)
                .withScreenClass(CustomerEdit.class)
                .withAfterCloseListener(afterScreenCloseEvent -> {
                    if (afterScreenCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        Customer committedCustomer = (afterScreenCloseEvent.getSource()).getEditedEntity();
                        System.out.println("Updated " + committedCustomer);
                    }
                })
                .build()
                .show();
    }
    // end::edit-action-performed-event-2[]
    // tag::refresh-action-performed-event[]
    @Subscribe("custTable.refresh")
    public void onCustTableRefresh(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Are you sure you want to refresh the list?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> refreshAction.execute()), // execute action
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::refresh-action-performed-event[]
    // tag::refresh-action-performed-event-2[]
    @Subscribe("custTable.refreshAction")
    public void onCustTableRefreshAction(Action.ActionPerformedEvent event) {
        customersDl.load();
    }
    // end::refresh-action-performed-event-2[]
    // tag::remove-after-action-performed-handler[]
    @Install(to = "custTable.remove", subject = "afterActionPerformedHandler")
    private void custTableRemoveAfterActionPerformedHandler(RemoveOperation.AfterActionPerformedEvent<Customer> event) {
        System.out.println("Removed " + event.getItems());
    }
    // end::remove-after-action-performed-handler[]
    // tag::remove-action-cancelled-handler[]
    @Install(to = "custTable.remove", subject = "actionCancelledHandler")
    private void custTableRemoveActionCancelledHandler(RemoveOperation.ActionCancelledEvent<Customer> event) {
        System.out.println("Cancelled");
    }
    // end::remove-action-cancelled-handler[]
    // tag::remove-action-performed-event[]
    @Subscribe("custTable.remove")
    public void onCustTableRemove(Action.ActionPerformedEvent event) {
        removeAction.setConfirmation(false);
        dialogs.createOptionDialog()
                .withCaption("My confirm dialog")
                .withMessage("Do you really want to remove the customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> removeAction.execute()), // execute action
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::remove-action-performed-event[]
    // tag::remove-action-performed-event-2[]
    @Subscribe("custTable.removeAction")
    public void onCustTableRemoveAction(Action.ActionPerformedEvent event) {
        removeOperation.builder(custTable)
                .withConfirmationTitle("Removing customer...")
                .withConfirmationMessage("Do you really want to remove the customer?")
                .remove();
    }
    // end::remove-action-performed-event-2[]
    // tag::view-screen-options-supplier[]
    @Install(to = "custTable.view", subject = "screenOptionsSupplier")
    private ScreenOptions custTableViewScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("someParameter", 10));
    }
    // end::view-screen-options-supplier[]
    // tag::view-screen-configurer[]
    @Install(to = "custTable.view", subject = "screenConfigurer")
    private void custTableViewScreenConfigurer(Screen screen) {
        ((CustomerEdit) screen).setSomeParameter(10);
    }
    // end::view-screen-configurer[]
    // tag::view-transformation[]
    @Install(to = "custTable.view", subject = "transformation")
    private Customer custTableViewTransformation(Customer customer) {
        return reloadCustomer(customer);
    }
    // end::view-transformation[]
    // tag::view-after-commit-handler[]
    @Install(to = "custTable.view", subject = "afterCommitHandler")
    private void custTableViewAfterCommitHandler(Customer customer) {
        System.out.println("Updated " + customer);
    }
    // end::view-after-commit-handler[]
    // tag::view-after-close-handler[]
    @Install(to = "custTable.view", subject = "afterCloseHandler")
    private void custTableViewAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
            System.out.println("Enabled editing and then committed");
        }
    }
    // end::view-after-close-handler[]
    // tag::view-action-performed-event[]
    @Subscribe("custTable.view")
    public void onCustTableView(Action.ActionPerformedEvent event) {
        CustomerEdit customerEdit = screenBuilders.editor(custTable)
                .withOpenMode(OpenMode.DIALOG)
                .withScreenClass(CustomerEdit.class)
                .withAfterCloseListener(afterScreenCloseEvent -> {
                    if (afterScreenCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        Customer committedCustomer = (afterScreenCloseEvent.getSource()).getEditedEntity();
                        System.out.println("Updated " + committedCustomer);
                    }
                })
                .build();
        customerEdit.setReadOnly(true);
        customerEdit.show();
    }

    // end::view-action-performed-event[]
    // tag::tag-lookup-screen-options-supplier[]
    @Install(to = "customerTagPicker.tagLookup", subject = "screenOptionsSupplier")
    private ScreenOptions customerTagPickerTagLookupScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("someParameter", 10));
    }
    // end::tag-lookup-screen-options-supplier[]
    // tag::tag-lookup-screen-configurer[]
    @Install(to = "customerTagPicker.tagLookup", subject = "screenConfigurer")
    private void customerTagPickerTagLookupScreenConfigurer(Screen screen) {
        ((CustomerBrowse) screen).setSomeParameter(10);
    }
    // end::tag-lookup-screen-configurer[]
    // tag::tag-lookup-select-validator[]
    @Install(to = "customerTagPicker.tagLookup", subject = "selectValidator")
    private boolean customerTagPickerTagLookupSelectValidator(LookupScreen.ValidationContext<Customer> validationContext) {
        boolean valid = validationContext.getSelectedItems().size() == 1;
        if (!valid) {
            notifications.create().withCaption("Select a single customer").show();
        }
        return valid;
    }
    // end::tag-lookup-select-validator[]
    // tag::tag-lookup-transformation[]
    @Install(to = "customerTagPicker.tagLookup", subject = "transformation")
    private Collection<Customer> customerTagPickerTagLookupTransformation(Collection<Customer> collection) {
        return reloadCustomers(collection);
    }
    // end::tag-lookup-transformation[]
    private Collection<Customer> reloadCustomers(Collection<Customer> customers){
        return customers;
    }
    // tag::tag-lookup-after-close-handler[]
    @Install(to = "customerTagPicker.tagLookup", subject = "afterCloseHandler")
    private void customerTagPickerTagLookupAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        if (afterCloseEvent.closedWith(StandardOutcome.SELECT)) {
            System.out.println("Selected");
        }
    }
    // end::tag-lookup-after-close-handler[]
    // tag::tag-lookup-action-performed-event[]
    @Subscribe("customerTagPicker.tagLookup")
    public void onCustomerTagPickerTagLookup(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Do you really want to select a customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> tagLookupAction.execute()), // <1>
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::tag-lookup-action-performed-event[]
    // tag::tag-lookup-action-performed-event-2[]
    @Subscribe("customerTagPicker.tag")
    public void onCustomerTagPickerTag(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(customerTagPicker)
                .withOpenMode(OpenMode.DIALOG)
                .withScreenClass(CustomerBrowse.class)
                .withSelectValidator(customerValidationContext -> {
                    boolean valid = customerValidationContext.getSelectedItems().size() == 1;
                    if (!valid) {
                        notifications.create().withCaption("Select a single customer").show();
                    }
                    return valid;

                })
                .build()
                .show();
    }
    // end::tag-lookup-action-performed-event-2[]

}