package sample.screen.customer;

import io.jmix.ui.screen.*;
import sample.entity.Customer;

@UiController("sample_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}