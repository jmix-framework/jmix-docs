package ui.ex1.screen.customer;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Customer;

@UiController("uiex1_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}