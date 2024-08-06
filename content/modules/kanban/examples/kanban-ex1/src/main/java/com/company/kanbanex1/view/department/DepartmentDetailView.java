package com.company.kanbanex1.view.department;

import com.company.kanbanex1.entity.Department;
import com.company.kanbanex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "departments/:id", layout = MainView.class)
@ViewController("Department.detail")
@ViewDescriptor("department-detail-view.xml")
@EditedEntityContainer("departmentDc")
public class DepartmentDetailView extends StandardDetailView<Department> {
}