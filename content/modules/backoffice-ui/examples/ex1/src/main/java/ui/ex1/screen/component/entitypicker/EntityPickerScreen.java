package ui.ex1.screen.component.entitypicker;

import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.*;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.action.DialogAction;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.action.entitypicker.EntityLookupAction;
import io.jmix.ui.action.entitypicker.EntityOpenAction;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.VBoxLayout;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.entity.User;
import ui.ex1.screen.entity.customer.CustomerBrowse;
import ui.ex1.screen.entity.customer.CustomerEdit;

import javax.inject.Named;
import java.util.Collection;

@UiController("entityPicker-screen")
@UiDescriptor("entityPicker-screen.xml")
public class EntityPickerScreen extends Screen {
    // tag::userPicker1[]
    @Autowired
    private Metadata metadata;
    @Autowired
    private UiComponents uiComponents;
    // tag::addAction[]
    @Autowired
    private Actions actions;
    // end::userPicker1[]
    @Autowired
    private EntityPicker<Customer> entityPicker;

    // end::addAction[]
    // tag::customerEntityPicker[]
    @Autowired
    private EntityPicker<Customer> customerEntityPicker;
    @Autowired
    private Notifications notifications;
    // end::customerEntityPicker[]
    @Autowired
    private MetadataTools metadataTools;
    @Autowired
    private Dialogs dialogs;
    // tag::inject-customerFieldClear[]
    @Named("customerField.clear")
    private EntityClearAction customerFieldClear;

    // end::inject-customerFieldClear[]
    // tag::inject-customer-lookup[]
    @Named("cEntityField.lookup")
    private EntityLookupAction<Customer> entityLookupAction;

    // end::inject-customer-lookup[]

    // tag::inject-cEntityField[]
    @Autowired
    private EntityPicker<Customer> cEntityField;

    // end::inject-cEntityField[]
    // tag::inject-screen-builders[]
    @Autowired
    private ScreenBuilders screenBuilders;

    // end::inject-screen-builders[]
    // tag::inject-open-action[]
    @Named("cEntityField.open")
    private EntityOpenAction<Customer> openAction;

    // end::inject-open-action[]
    // tag::inject-customerEp[]
    @Autowired
    private EntityPicker<Customer> customerEp;

    // end::inject-customerEp[]
    @Autowired
    private VBoxLayout vbox;

    // tag::on-init-start[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::on-init-start[]
        // tag::addAction2[]
        entityPicker.addAction(actions.create(EntityOpenAction.class));
        // end::addAction2[]
        // tag::set-entity-lookup-action[]
        entityLookupAction.setOpenMode(OpenMode.DIALOG);
        entityLookupAction.setScreenClass(CustomerBrowse.class);
        // end::set-entity-lookup-action[]
        // tag::set-entity-open-action[]
        openAction.setOpenMode(OpenMode.DIALOG);
        openAction.setScreenClass(CustomerEdit.class);
        // end::set-entity-open-action[]
        // tag::add-custom-action[]
        customerEp.addAction(new BaseAction("showLevel")
                .withCaption(null)
                .withDescription(null)
                .withIcon(JmixIcon.VIEW_ACTION.source())
                .withHandler(e -> {
                    Customer customer = customerEp.getValue();
                    if (customer != null) {
                        notifications.create()
                                .withCaption(customer.getFirstName() + " " +
                                        customer.getLastName() +
                                        "'s level is " + customer.getLevel())
                                .show();
                    } else {
                        notifications.create()
                                .withCaption("Choose a customer")
                                .show();
                    }
                }));
        // end::add-custom-action[]
        // tag::userPicker3[]
        EntityPicker<User> userPicker = uiComponents.create(EntityPicker.of(User.class));
        userPicker.setMetaClass(metadata.getClass(User.class));
        userPicker.addAction(actions.create(EntityLookupAction.class));
        userPicker.addAction(actions.create(EntityOpenAction.class));
        userPicker.addAction(actions.create(EntityClearAction.class));
        vbox.add(userPicker);
        // tag::on-init-end[]
    }
    // end::on-init-end[]
    // end::userPicker3[]
    // tag::customAction[]

    @Subscribe("customerEntityPicker.points")  // <1>
    public void onCustomerEntityPickerPoints(Action.ActionPerformedEvent event) {
        Customer customer = customerEntityPicker.getValue();
        if (customer != null) {
            notifications.create()
                    .withCaption(customer.getFirstName() +
                            " has " + customer.getRewardPoints() +
                            " reward points")
                    .show();
        } else {
            notifications.create()
                    .withCaption("Choose a customer")
                    .show();
        }
    }
    // end::customAction[]

    @Subscribe("custPicker")
    public void onCustPickerValueChange(HasValue.ValueChangeEvent<Customer> event) {
        String str = event.getValue() == null ? "null" : metadataTools.getInstanceName(event.getValue());
        notifications.create()
                .withCaption("value = " + str)
                .show();
        // tag::end[]
    }
    // end::end[]
    // tag::fieldIconProvider[]
    @Install(to = "customerField", subject = "fieldIconProvider")
    private String customerFieldFieldIconProvider(Customer customer) { // <1>
        return (customer != null) ? "font-icon:CHECK" : "font-icon:BAN";
    }
    // end::fieldIconProvider[]
    // tag::entity-clear-action-performed-event[]
    @Subscribe("customerField.clear")
    public void onCustomerFieldClear(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Do you really want to clear the field?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> customerFieldClear.execute()), // execute action
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::entity-clear-action-performed-event[]
    // tag::lookup-screen-options-supplier[]
    @Install(to = "cEntityField.lookup", subject = "screenOptionsSupplier")
    private ScreenOptions cEntityFieldLookupScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("someParameter", 10));
    }
    // end::lookup-screen-options-supplier[]
    // tag::lookup-screen-configurer[]
    @Install(to = "cEntityField.lookup", subject = "screenConfigurer")
    private void cEntityFieldLookupScreenConfigurer(Screen screen) {
        ((CustomerBrowse) screen).setSomeParameter(10);
    }
    // end::lookup-screen-configurer[]
    // tag::lookup-select-validator[]
    @Install(to = "cEntityField.lookup", subject = "selectValidator")
    private boolean cEntityFieldLookupSelectValidator(LookupScreen.ValidationContext<Customer> validationContext) {
        boolean valid = validationContext.getSelectedItems().size() == 1;
        if (!valid) {
            notifications.create().withCaption("Select a single customer").show();
        }
        return valid;
    }
    // end::lookup-select-validator[]
    // tag::lookup-transformation[]
    @Install(to = "cEntityField.lookup", subject = "transformation")
    private Collection<Customer> cEntityFieldLookupTransformation(Collection<Customer> collection) {
        return reloadCustomers(collection);
    }
    // end::lookup-transformation[]
    private Collection<Customer> reloadCustomers(Collection<Customer> customers) {
        return customers;
    }

    // tag::lookup-after-close-handler[]
    @Install(to = "cEntityField.lookup", subject = "afterCloseHandler")
    private void cEntityFieldLookupAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        if (afterCloseEvent.closedWith(StandardOutcome.SELECT)) {
            System.out.println("Selected");
        }
    }
    // end::lookup-after-close-handler[]
    // tag::lookup-action-performed-event[]
    @Subscribe("cEntityField.lookup")
    public void onCEntityFieldLookup(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Do you really want to select a customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> entityLookupAction.execute()), // execute action
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::lookup-action-performed-event[]
    // tag::lookup-action-performed-event-2[]
    @Subscribe("cEntityField.lookupAction")
    public void onCEntityFieldLookupAction(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(cEntityField)
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
    // end::lookup-action-performed-event-2[]
    // tag::open-screen-options-supplier[]
    @Install(to = "cEntityField.open", subject = "screenOptionsSupplier")
    private ScreenOptions cEntityFieldOpenScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("someParameter", 10));
    }
    // end::open-screen-options-supplier[]
    // tag::open-screen-configurer[]
    @Install(to = "cEntityField.open", subject = "screenConfigurer")
    private void cEntityFieldOpenScreenConfigurer(Screen screen) {
        ((CustomerEdit) screen).setSomeParameter(10);
    }
    // end::open-screen-configurer[]
    // tag::open-after-close-handler[]
    @Install(to = "cEntityField.open", subject = "afterCloseHandler")
    private void cEntityFieldOpenAfterCloseHandler(AfterCloseEvent afterCloseEvent) {
        System.out.println("Closed with " + afterCloseEvent.getCloseAction());
    }
    // end::open-after-close-handler[]
    // tag::open-action-performed-event[]
    @Subscribe("cEntityField.open")
    public void onCEntityFieldOpen(Action.ActionPerformedEvent event) {
        dialogs.createOptionDialog()
                .withCaption("Please confirm")
                .withMessage("Do you really want to open the customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> openAction.execute()), // <1>
                        new DialogAction(DialogAction.Type.NO)
                )
                .show();
    }
    // end::open-action-performed-event[]
    // tag::open-action-performed-event-2[]
    @Subscribe("cEntityField.openAction")
    public void onCEntityFieldOpenAction(Action.ActionPerformedEvent event) {
        screenBuilders.editor(cEntityField)
                .withOpenMode(OpenMode.DIALOG)
                .withScreenClass(CustomerEdit.class)
                .build()
                .show();
    }
    // end::open-action-performed-event-2[]
}