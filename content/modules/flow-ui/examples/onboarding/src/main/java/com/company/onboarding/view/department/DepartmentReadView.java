package com.company.onboarding.view.department;

import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

// tag::annotations[]
// common annotations
@ViewController("Department.read")
@ViewDescriptor("department-read-view.xml")
@Route(value = "departments/:id/read", layout = MainView.class)
// read view annotations
@ReadEntityContainer("departmentDc")
@PrimaryReadView(Department.class)
public class DepartmentReadView extends StandardReadView<Department> {
// end::annotations[]
}
