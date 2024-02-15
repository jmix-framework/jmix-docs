package com.company.demo.view.customer;

import com.company.demo.actions.CustomExportAction;
import com.company.demo.entity.Customer;

import com.company.demo.exporters.CustomExporter;
import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.Actions;
import io.jmix.flowui.component.ListDataComponent;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.gridexportflowui.action.ExcelExportAction;
import io.jmix.gridexportflowui.action.JsonExportAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Route(value = "customers", layout = MainView.class)
@ViewController("Customer.list")
@ViewDescriptor("customer-list-view.xml")
@LookupComponent("customersDataGrid")
@DialogMode(width = "64em")
public class CustomerListView extends StandardListView<Customer> {
    @Autowired
    private Actions actions;
    @Autowired
    private ApplicationContext applicationContext;
    @ViewComponent
    private DataGrid<Customer> customersDataGrid;
    @ViewComponent
    private JmixButton customBtn;
    // tag::excelExport[]
    @ViewComponent("customersDataGrid.excelExport")
    private ExcelExportAction customersDataGridExcelExport;

    // end::excelExport[]
    // tag::jsonExport[]
    @ViewComponent("customersDataGrid.jsonExport")
    private JsonExportAction customersDataGridJsonExport;

    // end::jsonExport[]

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        CustomExportAction customAction = actions.create(CustomExportAction.ID);

        customAction.setDataGridExporter(applicationContext.getBean(CustomExporter.class));
        customAction.setTarget((ListDataComponent)customersDataGrid);
        customAction.setApplicationContext(applicationContext);

        customBtn.setAction(customAction);
        // tag::excelExport[]
        customersDataGridExcelExport.setDataGridExporter(applicationContext.getBean(CustomExporter.class));
        customersDataGridExcelExport.setDescription("Action with custom exporter"); // <1>
        // end::excelExport[]
        // tag::jsonExport[]
        customersDataGridJsonExport.setDataGridExporter(applicationContext.getBean(CustomExporter.class));
        customersDataGridJsonExport.setDescription("Action with custom exporter"); // <1>
        // end::jsonExport[]
        // tag::onInit[]
    }
    // end::onInit[]
}