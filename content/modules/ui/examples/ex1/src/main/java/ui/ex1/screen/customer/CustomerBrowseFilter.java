package ui.ex1.screen.customer;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Customer;

@UiController("uiex1_Customer.browse-filter")
@UiDescriptor("customer-browse-filter.xml")
@LookupComponent("customersTable")
public class CustomerBrowseFilter extends StandardLookup<Customer> {
}