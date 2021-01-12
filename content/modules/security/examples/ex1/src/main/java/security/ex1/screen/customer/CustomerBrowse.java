package security.ex1.screen.customer;

import io.jmix.ui.screen.*;
import security.ex1.entity.Customer;

@UiController("sample_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}