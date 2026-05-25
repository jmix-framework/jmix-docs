package multitenancy.ex1.screen.customer;

import io.jmix.ui.screen.*;
import multitenancy.ex1.entity.Customer;

@UiController("mtex1_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}