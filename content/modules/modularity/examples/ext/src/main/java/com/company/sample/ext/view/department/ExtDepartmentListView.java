package com.company.sample.ext.view.department;

import com.company.sample.base.view.department.DepartmentListView;
import com.company.sample.ext.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

// tag::extend[]
@Route(value = "departments", layout = MainView.class)
@ViewController("base_Department.list")
@ViewDescriptor("ext-department-list-view.xml")
public class ExtDepartmentListView extends DepartmentListView {
// end::extend[]
}