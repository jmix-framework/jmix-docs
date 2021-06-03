package ui.ex1.screen.entity.department;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Department;

@UiController("uiex1_Department.browse")
@UiDescriptor("department-browse.xml")
@LookupComponent("departmentsTable")
public class DepartmentBrowse extends StandardLookup<Department> {
}