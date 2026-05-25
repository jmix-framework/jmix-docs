package audit.ex1.screen.customer;

import io.jmix.ui.screen.*;
import audit.ex1.entity.Customer;

@UiController("ex1_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}