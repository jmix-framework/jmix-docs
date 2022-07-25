package modularity.sample.base.screen.department;

import io.jmix.ui.screen.*;
import modularity.sample.base.entity.Department;

@UiController("base_Department.edit")
@UiDescriptor("department-edit.xml")
@EditedEntityContainer("departmentDc")
public class DepartmentEdit extends StandardEditor<Department> {
}