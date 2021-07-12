package ui.ex1.screen.data;

import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Employee;
import ui.ex1.entity.EquipmentLine;

// tag::depend[]
@UiController("uiex1_EmployeeDependTables")
@UiDescriptor("employee-depend-tables.xml")
@LookupComponent("employeesTable")
public class EmployeeDependTables extends StandardLookup<Employee> {

    @Autowired
    private CollectionLoader<Employee> employeesDl;

    @Autowired
    private CollectionLoader<EquipmentLine> equipmentLinesDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        employeesDl.load(); // <1>
    }

    @Subscribe(id = "employeesDc", target = Target.DATA_CONTAINER)
    public void onEmployeesDcItemChange(InstanceContainer.ItemChangeEvent<Employee> event) {
        equipmentLinesDl.setParameter("employee", event.getItem()); // <2>
        equipmentLinesDl.load();
    }
}
// end::depend[]