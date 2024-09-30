package com.company.demo.view.customer;

import com.company.demo.entity.Customer;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.view.*;


@Route(value = "customers", layout = MainView.class)
@ViewController("Customer.list")
@ViewDescriptor("customer-list-view.xml")
@LookupComponent("customersDataGrid")
@DialogMode(width = "64em")
public class CustomerListView extends StandardListView<Customer> {

    @Subscribe("customersDataGrid.importCustomersAction")
    public void onCustomersDataGridImportCustomers(final ActionPerformedEvent event) {
        System.out.println(">>> customersDataGrid.importCustomersAction");
    }
}