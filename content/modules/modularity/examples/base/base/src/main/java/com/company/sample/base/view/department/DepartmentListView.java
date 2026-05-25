package com.company.sample.base.view.department;

import com.company.sample.base.entity.Department;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "departments", layout = DefaultMainViewParent.class)
@ViewController("base_Department.list")
@ViewDescriptor("department-list-view.xml")
@LookupComponent("departmentsDataGrid")
@DialogMode(width = "64em")
public class DepartmentListView extends StandardListView<Department> {
}