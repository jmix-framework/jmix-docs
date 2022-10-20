package com.company.onboarding.view.department;

import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

// tag::annotations[]
// common annotations
@ViewController("Department.list")
@ViewDescriptor("department-list-view.xml")
@Route(value = "departments", layout = MainView.class)
@DialogMode(width = "50em", height = "37.5em")
// list view annotations
@LookupComponent("departmentsTable")
@PrimaryLookupView(Department.class)
public class DepartmentListView extends StandardListView<Department> {
// end::annotations[]
}