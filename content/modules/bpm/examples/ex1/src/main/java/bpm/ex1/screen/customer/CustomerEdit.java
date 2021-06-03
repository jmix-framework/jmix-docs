package bpm.ex1.screen.customer;

import io.jmix.ui.screen.*;
import bpm.ex1.entity.Customer;

@UiController("smpl_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}