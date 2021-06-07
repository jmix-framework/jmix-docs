package ui.ex1.screen.data;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.ui.component.Button;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Department;

import static io.jmix.ui.screen.UiControllerUtils.getFrame;

@UiController("uiex1_DepartmentData2.browse")
@UiDescriptor("department-browse-data2.xml")
@LookupComponent("departmentsTable")
public class DepartmentBrowseData2 extends StandardLookup<Department> {

    // tag::data-context[]
    @Autowired
    private DataContext dataContext;

    // end::data-context[]
    // tag::data-manager[]
    @Autowired
    private DataManager dataManager;

    // end::data-manager[]
    // tag::collection[]
    @Autowired
    private CollectionContainer<Department> departmentsDc;

    // end::collection[]

    // tag::load[]
    private void loadDepartment(Id<Department> departmentId) {
        Department department = dataManager.load(departmentId).one();
        Department trackedDepartment = dataContext.merge(department);
        departmentsDc.getMutableItems().add(trackedDepartment);
    }
    // end::load[]

}