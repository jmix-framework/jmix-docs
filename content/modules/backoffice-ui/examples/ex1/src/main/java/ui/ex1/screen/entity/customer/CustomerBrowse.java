package ui.ex1.screen.entity.customer;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Customer;

@UiController("uiex1_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}