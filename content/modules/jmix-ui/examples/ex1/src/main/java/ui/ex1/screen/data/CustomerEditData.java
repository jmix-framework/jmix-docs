package ui.ex1.screen.data;

import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

@UiController("uiex1_CustomerData.edit")
@UiDescriptor("customer-edit-data.xml")
@EditedEntityContainer("customerDc")
public class CustomerEditData extends StandardEditor<Customer> {

    @Autowired
    private Notifications notifications;

    // tag::property-change[]
    @Subscribe(id = "customerDc", target = Target.DATA_CONTAINER)
    public void onCustomerDcItemPropertyChange(
            InstanceContainer.ItemPropertyChangeEvent<Customer> event) {
        Customer customer = event.getItem();
        String changedProperty = event.getProperty();
        Object currentValue = event.getValue();
        Object previousValue = event.getPrevValue();
        // ...
    }
    // end::property-change[]

    // tag::item-change[]
    @Subscribe(id = "customerDc", target = Target.DATA_CONTAINER)
    public void onCustomerDcItemChange(InstanceContainer.ItemChangeEvent<Customer> event) {
        Customer customer = event.getItem();
        Customer previouslySelectedCustomer = event.getPrevItem();
        // ...
    }
    // end::item-change[]
}