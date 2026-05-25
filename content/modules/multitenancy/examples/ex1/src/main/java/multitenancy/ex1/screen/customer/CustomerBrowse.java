package multitenancy.ex1.screen.customer;

import io.jmix.ui.screen.*;
import multitenancy.ex1.entity.Customer;

@UiController("mtex1_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}