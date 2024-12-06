package com.company.demo.view.customers;


import com.company.demo.entity.Customer;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "customers-view", layout = MainView.class)
@ViewController(id = "CustomersView")
@ViewDescriptor(path = "customers-view.xml")
public class CustomersView extends StandardView {
    // tag::columnsExportFilter[]
    @Install(to = "customersDataGrid.excelExport", subject = "columnsExportFilter")
    private boolean customersDataGridExcelExportColumnsExportFilter(
            final Grid.Column<Customer> column) {
        return column.getKey().contains("firstName")
                || column.getKey().contains("email");
    }
    // end::columnsExportFilter[]
}