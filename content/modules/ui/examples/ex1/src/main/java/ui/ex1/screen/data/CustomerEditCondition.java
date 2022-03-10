package ui.ex1.screen.data;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Customer;

@UiController("uiex1_CustomerCondition.edit")
@UiDescriptor("customer-edit-condition.xml")
@EditedEntityContainer("customerDc")
public class CustomerEditCondition extends StandardEditor<Customer> {
}