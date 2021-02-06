package audit.ex1.screen.customer;

import io.jmix.ui.screen.*;
import audit.ex1.entity.Customer;

@UiController("ex1_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}