package sample.screen.customer;

import io.jmix.ui.screen.*;
import sample.entity.Customer;
import sample.entity.CustomerGrade;

@UiController("sample_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {

    @Subscribe
    public void onInitEntity(InitEntityEvent<Customer> event) {
        event.getEntity().setGrade(CustomerGrade.BRONZE);
    }

}