package modularity.sample.base.screen.department;

import io.jmix.ui.screen.*;
import modularity.sample.base.entity.Department;

@UiController("base_Department.browse")
@UiDescriptor("department-browse.xml")
@LookupComponent("departmentsTable")
public class DepartmentBrowse extends StandardLookup<Department> {
}