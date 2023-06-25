package com.company.demo.view.customer;

import com.company.demo.entity.Customer;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.session.SessionData;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers", layout = MainView.class)
@ViewController("Customer.list")
@ViewDescriptor("customer-list-view.xml")
@LookupComponent("customersTable")
@DialogMode(width = "50em", height = "37.5em")
// tag::session-data[]
public class CustomerListView extends StandardListView<Customer> {

    @Autowired
    private SessionData sessionData;
    // end::session-data[]
}