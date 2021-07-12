package ui.ex1.screen.data;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.component.data.options.ContainerOptions;
import io.jmix.ui.component.data.table.ContainerTableItems;
import io.jmix.ui.component.data.value.ContainerValueSource;
import io.jmix.ui.model.*;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Department;
import ui.ex1.entity.Employee;
import ui.ex1.entity.EquipmentLine;
import ui.ex1.entity.Position;

@UiController("uiex1_EmployeeExample.edit")
@UiDescriptor("employee-edit-example.xml")
public class EmployeeEditExample extends StandardEditor<Employee> {

    // tag::progr[]
    @Autowired
    private DataComponents dataComponents; // <1>
    @Autowired
    private UiComponents uiComponents;

    private InstanceContainer<Employee> employeeDc;
    private CollectionPropertyContainer<EquipmentLine> equipmentDc;
    private CollectionContainer<Department> departmentsDc;
    private InstanceLoader<Employee> employeeDl;
    private CollectionLoader<Department> departmentsDl;

    @Subscribe
    protected void onInit(InitEvent event) {
        createDataComponents();
        createUiComponents();
    }

    private void createDataComponents() {
        DataContext dataContext = dataComponents.createDataContext();
        getScreenData().setDataContext(dataContext); // <2>

        employeeDc = dataComponents.createInstanceContainer(Employee.class);

        employeeDl = dataComponents.createInstanceLoader();
        employeeDl.setContainer(employeeDc); // <3>
        employeeDl.setDataContext(dataContext); // <4>
        employeeDl.setFetchPlan("_base");

        equipmentDc = dataComponents.createCollectionContainer(
                EquipmentLine.class, employeeDc, "equipment"); // <5>

        departmentsDc = dataComponents.createCollectionContainer(Department.class);

        departmentsDl = dataComponents.createCollectionLoader();
        departmentsDl.setContainer(departmentsDc);
        departmentsDl.setDataContext(dataContext);
        departmentsDl.setQuery("select e from uiex1_Department e"); // <6>
        departmentsDl.setFetchPlan("_base");
    }

    private void createUiComponents() {

        Form form = uiComponents.create(Form.class);
        getWindow().add(form);

        TextField<String> nameField = uiComponents.create(TextField.TYPE_STRING);
        nameField.setValueSource(new ContainerValueSource<>(employeeDc, "name")); // <7>
        form.add(nameField);

        TextField<Double> salaryField = uiComponents.create(TextField.TYPE_DOUBLE);
        salaryField.setValueSource(new ContainerValueSource<>(employeeDc, "salary"));
        form.add(salaryField);

        ComboBox<Position> positionField = uiComponents.create(ComboBox.of(Position.class));
        positionField.setValueSource(new ContainerValueSource<>(employeeDc, "position"));
        form.add(positionField);

        EntityComboBox<Employee> employeeField = uiComponents.create(EntityComboBox.of(Employee.class));
        employeeField.setValueSource(new ContainerValueSource<>(employeeDc, "department"));
        employeeField.setOptions(new ContainerOptions(departmentsDc)); // <8>
        form.add(employeeField);

        Table<EquipmentLine> table = uiComponents.create(Table.of(EquipmentLine.class));
        getWindow().add(table);
        getWindow().expand(table);
        table.setItems(new ContainerTableItems<>(equipmentDc)); // <9>

        Button okButton = uiComponents.create(Button.class);
        okButton.setAction(getWindow().getActionNN(WINDOW_COMMIT_AND_CLOSE));
        getWindow().add(okButton);

        Button cancelButton = uiComponents.create(Button.class);
        cancelButton.setAction(getWindow().getActionNN(WINDOW_CLOSE));
        getWindow().add(cancelButton);
    }

    @Override
    protected InstanceContainer<Employee> getEditedEntityContainer() { // <10>
        return employeeDc;
    }

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) { // <11>
        employeeDl.load();
        departmentsDl.load();
    }
    // end::progr[]
}
