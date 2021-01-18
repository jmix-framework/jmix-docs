package datamodel.ex1.screen.customer;

import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Customer;
import datamodel.ex1.entity.CustomerGrade;

@UiController("sample_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {

    @Subscribe
    public void onInitEntity(InitEntityEvent<Customer> event) {
        event.getEntity().setGrade(CustomerGrade.BRONZE);
    }

}