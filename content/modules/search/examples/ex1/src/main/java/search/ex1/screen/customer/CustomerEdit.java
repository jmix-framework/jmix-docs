package search.ex1.screen.customer;

import io.jmix.ui.screen.*;
import search.ex1.entity.Customer;

@UiController("search_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}