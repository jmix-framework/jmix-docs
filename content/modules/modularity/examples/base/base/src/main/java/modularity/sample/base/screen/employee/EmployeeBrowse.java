package modularity.sample.base.screen.employee;

import io.jmix.ui.screen.*;
import modularity.sample.base.entity.Employee;

@UiController("base_Employee.browse")
@UiDescriptor("employee-browse.xml")
@LookupComponent("employeesTable")
public class EmployeeBrowse extends StandardLookup<Employee> {
}