package com.company.onboarding.view.department;

import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "departments-lookup", layout = MainView.class)
@ViewController("Department.lookup")
@ViewDescriptor("department-lookup-view.xml")
@LookupComponent("departmentsTable")
@DialogMode(width = "50em", height = "37.5em")
public class DepartmentLookupView extends StandardListView<Department> {
}