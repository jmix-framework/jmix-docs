package sample.screen.component.entitypicker;

import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.ui.Actions;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.entitypicker.EntityClearAction;
import io.jmix.ui.action.entitypicker.LookupAction;
import io.jmix.ui.action.entitypicker.OpenAction;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import sample.entity.Customer;
import sample.entity.User;

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
    // tag::userPicker2[]
    // tag::addAction2[]

    @Subscribe
    public void onInit(InitEvent event) {
        // end::userPicker2[]
        entityPicker.addAction(actions.create(OpenAction.class));
        // end::addAction2[]
        // tag::userPicker3[]
        EntityPicker<User> userPicker = uiComponents.create(EntityPicker.of(User.class));
        userPicker.setMetaClass(metadata.getClass(User.class));
        userPicker.addAction(actions.create(LookupAction.class));
        userPicker.addAction(actions.create(OpenAction.class));
        userPicker.addAction(actions.create(EntityClearAction.class));
        getWindow().add(userPicker);
    }
    // end::userPicker3[]
    // tag::customAction[]

    @Subscribe("customerEntityPicker.points")  // <1>
    public void onCustomerEntityPickerPoints(Action.ActionPerformedEvent event) {
        Customer customer = customerEntityPicker.getValue();
        if (customer != null) {
            notifications.create()
                    .withCaption(customer.getFirstName() + " has " + customer.getRewardPoints() + " reward points")
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
    }
    // tag::fieldIconProvider[]
    @Install(to = "customerField", subject = "fieldIconProvider")
    private String customerFieldFieldIconProvider(Customer customer) { // <1>
        return (customer!= null) ? "font-icon:CHECK" : "font-icon:BAN";
    }
    // end::fieldIconProvider[]
}