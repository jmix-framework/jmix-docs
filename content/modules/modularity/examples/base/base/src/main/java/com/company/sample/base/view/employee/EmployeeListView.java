package com.company.sample.base.view.employee;

import com.company.sample.base.entity.Employee;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "employees", layout = DefaultMainViewParent.class)
@ViewController("base_Employee.list")
@ViewDescriptor("employee-list-view.xml")
@LookupComponent("employeesDataGrid")
@DialogMode(width = "64em")
public class EmployeeListView extends StandardListView<Employee> {
}