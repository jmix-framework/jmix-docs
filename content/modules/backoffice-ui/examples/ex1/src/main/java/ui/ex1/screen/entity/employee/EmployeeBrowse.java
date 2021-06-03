package ui.ex1.screen.entity.employee;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Employee;

@UiController("uiex1_Employee.browse")
@UiDescriptor("employee-browse.xml")
@LookupComponent("employeesTable")
public class EmployeeBrowse extends StandardLookup<Employee> {
}