package bpm.ex1.screen.customer;

import io.jmix.ui.screen.*;
import bpm.ex1.entity.Customer;

@UiController("smpl_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}