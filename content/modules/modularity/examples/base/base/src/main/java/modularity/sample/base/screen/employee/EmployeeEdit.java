package modularity.sample.base.screen.employee;

import io.jmix.ui.screen.*;
import modularity.sample.base.entity.Employee;

@UiController("base_Employee.edit")
@UiDescriptor("employee-edit.xml")
@EditedEntityContainer("employeeDc")
public class EmployeeEdit extends StandardEditor<Employee> {
}