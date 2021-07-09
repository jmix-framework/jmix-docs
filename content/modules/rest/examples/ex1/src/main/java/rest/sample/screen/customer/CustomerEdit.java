package rest.sample.screen.customer;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import rest.sample.entity.Customer;

@UiController("sample_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}