package com.company.onboarding.view.salariesdashboard;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.supersetflowui.component.SupersetDashboard;
import io.jmix.supersetflowui.component.dataconstraint.DatasetConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "salaries-dashboard-view", layout = MainView.class)
@ViewController("SalariesDashboardView")
@ViewDescriptor("salaries-dashboard-view.xml")
public class SalariesDashboardView extends StandardView {
}