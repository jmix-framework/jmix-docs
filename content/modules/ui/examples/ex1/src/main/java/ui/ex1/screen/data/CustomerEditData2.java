package ui.ex1.screen.data;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Customer;

@UiController("uiex1_CustomerData2.edit")
@UiDescriptor("customer-edit-data2.xml")
@EditedEntityContainer("customerDc")
public class CustomerEditData2 extends StandardEditor<Customer> {
}