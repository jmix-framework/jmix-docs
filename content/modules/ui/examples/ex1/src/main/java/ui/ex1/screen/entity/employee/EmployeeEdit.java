package ui.ex1.screen.entity.employee;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Employee;

@UiController("uiex1_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
public class EmployeeEdit extends StandardEditor<Employee> {
}