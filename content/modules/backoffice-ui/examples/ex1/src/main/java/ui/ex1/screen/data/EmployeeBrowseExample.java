package ui.ex1.screen.data;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Employee;

@UiController("uiex1_EmployeeExample.browse")
@UiDescriptor("employee-browse-example.xml")
@LookupComponent("employeesTable")
public class EmployeeBrowseExample extends StandardLookup<Employee> {
}