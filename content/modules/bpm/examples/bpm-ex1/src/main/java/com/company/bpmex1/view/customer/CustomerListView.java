package com.company.bpmex1.view.customer;

import com.company.bpmex1.entity.Customer;

import com.company.bpmex1.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "customers2", layout = MainView.class)
@ViewController("smpl_Customer.list")
@ViewDescriptor("customer-list-view.xml")
@LookupComponent("customersDataGrid")
@DialogMode(width = "64em")
public class CustomerListView extends StandardListView<Customer> {
}