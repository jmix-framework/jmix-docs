package search.ex1.screen.customer;

import io.jmix.ui.screen.*;
import search.ex1.entity.Customer;

@UiController("search_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}