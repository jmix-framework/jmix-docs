package ui.ex1.screen.entity.department;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Department;

@UiController("uiex1_Department.edit")
@UiDescriptor("department-edit.xml")
@EditedEntityContainer("departmentDc")
public class DepartmentEdit extends StandardEditor<Department> {
}