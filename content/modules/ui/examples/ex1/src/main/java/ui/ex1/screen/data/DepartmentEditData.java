package ui.ex1.screen.data;

import io.jmix.ui.component.Button;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Department;
import ui.ex1.entity.Employee;
import ui.ex1.entity.Position;

import java.util.List;
import java.util.stream.Collectors;

@UiController("uiex1_DepartmentData.edit")
@UiDescriptor("department-edit-data.xml")
@EditedEntityContainer("departmentDc")
public class DepartmentEditData extends StandardEditor<Department> {

    // tag::filter[]
    @Autowired
    private CollectionPropertyContainer<Employee> employeesDc;

    private void filterByPosition(Position position) {
        List<Employee> filtered = getEditedEntity().getEmployees().stream()
                .filter(employee -> employee.getPosition().equals(position))
                .collect(Collectors.toList());
        employeesDc.setDisconnectedItems(filtered);
    }

    private void resetFilter() {
        employeesDc.setDisconnectedItems(getEditedEntity().getEmployees());
    }
    // end::filter[]

    @Subscribe("filterBtn")
    public void onFilterBtnClick(Button.ClickEvent event) {
        filterByPosition(Position.BA);
    }

    // tag::before-show[]
    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        getScreenData().loadAll();
    }
    // end::before-show[]
}