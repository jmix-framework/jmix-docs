package ui.ex1.screen.data;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Customer;

@UiController("uiex1_CustomerData3.edit")
@UiDescriptor("customer-edit-data3.xml")
@EditedEntityContainer("customerDc")
public class CustomerEditData3 extends StandardEditor<Customer> {
}