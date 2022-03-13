package ui.ex1.screen.data;

import io.jmix.ui.UiComponents;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Department;
import ui.ex1.entity.Employee;

@UiController("uiex1_DepartmentData2.edit")
@UiDescriptor("department-edit-data2.xml")
@EditedEntityContainer("departmentDc")
public class DepartmentEditData2 extends StandardEditor<Department> {

    @Autowired
    private UiComponents uiComponents;
    // tag::property-container[]
    @Autowired
    private DataComponents dataComponents;

    private InstanceContainer<Department> departmentDc;
    private CollectionPropertyContainer<Employee> employeesDc;


    private void createPropertyContainer() {
        employeesDc = dataComponents.createCollectionContainer(
                Employee.class, departmentDc, "employee");
    }
    // end::property-container[]

    @Subscribe
    public void onInit(InitEvent event) {
        departmentDc = dataComponents.createInstanceContainer(Department.class);
        createPropertyContainer();
    }


}