package security.ex1.screen.customer;

import io.jmix.ui.screen.*;
import security.ex1.entity.Customer;

@UiController("sample_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}