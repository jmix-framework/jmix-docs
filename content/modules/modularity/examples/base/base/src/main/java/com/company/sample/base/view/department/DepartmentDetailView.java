package com.company.sample.base.view.department;

import com.company.sample.base.entity.Department;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "departments/:id", layout = DefaultMainViewParent.class)
@ViewController("base_Department.detail")
@ViewDescriptor("department-detail-view.xml")
@EditedEntityContainer("departmentDc")
public class DepartmentDetailView extends StandardDetailView<Department> {
}