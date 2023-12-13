package com.company.onboardingmaps.view.department;

import com.company.onboardingmaps.entity.Department;

import com.company.onboardingmaps.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "departments", layout = MainView.class)
@ViewController("Department.list")
@ViewDescriptor("department-list-view.xml")
@LookupComponent("departmentsDataGrid")
@DialogMode(width = "50em", height = "37.5em")
public class DepartmentListView extends StandardListView<Department> {
}