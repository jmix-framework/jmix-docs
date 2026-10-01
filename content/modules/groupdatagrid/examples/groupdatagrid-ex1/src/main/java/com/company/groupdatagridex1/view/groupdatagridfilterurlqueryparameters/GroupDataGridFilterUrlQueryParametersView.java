package com.company.groupdatagridex1.view.groupdatagridfilterurlqueryparameters;

import com.company.groupdatagridex1.entity.Customer;
import com.company.groupdatagridex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "group-data-grid-filter-url-query-parameters", layout = MainView.class)
@ViewController("GroupDataGridFilterUrlQueryParametersView")
@ViewDescriptor("group-data-grid-filter-url-query-parameters-view.xml")
@LookupComponent("customersDataGrid")
@DialogMode(width = "64em")
public class GroupDataGridFilterUrlQueryParametersView extends StandardListView<Customer> {
}
