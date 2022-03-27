package appsettings.ex1.screen.customer;

import io.jmix.ui.screen.*;
import appsettings.ex1.entity.Customer;

@UiController("appsetex1_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}