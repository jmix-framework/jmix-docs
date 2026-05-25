package com.company.sample.base.view.employee;

import com.company.sample.base.entity.Employee;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "employees/:id", layout = DefaultMainViewParent.class)
@ViewController("base_Employee.detail")
@ViewDescriptor("employee-detail-view.xml")
@EditedEntityContainer("employeeDc")
public class EmployeeDetailView extends StandardDetailView<Employee> {
}