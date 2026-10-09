package com.company.onboarding.view.facets;

import com.company.onboarding.entity.User;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "data-grid-filter-url-query-parameters", layout = MainView.class)
@ViewController("DataGridFilterUrlQueryParametersView")
@ViewDescriptor("data-grid-filter-url-query-parameters-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "50em", height = "37.5em")
public class DataGridFilterUrlQueryParametersView extends StandardListView<User> {
}
