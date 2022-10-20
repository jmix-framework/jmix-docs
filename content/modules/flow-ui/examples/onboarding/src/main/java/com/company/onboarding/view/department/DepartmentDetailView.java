package com.company.onboarding.view.department;

import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

// tag::annotations[]
// common annotations
@ViewController("Department.detail")
@ViewDescriptor("department-detail-view.xml")
@Route(value = "departments/:id", layout = MainView.class)
// detail view annotations
@EditedEntityContainer("departmentDc")
@PrimaryDetailView(Department.class)
public class DepartmentDetailView extends StandardDetailView<Department> {
// end::annotations[]
}