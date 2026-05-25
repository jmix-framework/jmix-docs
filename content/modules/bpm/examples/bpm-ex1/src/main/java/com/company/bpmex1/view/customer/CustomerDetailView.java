package com.company.bpmex1.view.customer;

import com.company.bpmex1.entity.Customer;

import com.company.bpmex1.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "customers2/:id", layout = MainView.class)
@ViewController("smpl_Customer.detail")
@ViewDescriptor("customer-detail-view.xml")
@EditedEntityContainer("customerDc")
public class CustomerDetailView extends StandardDetailView<Customer> {
}