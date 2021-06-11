package ui.ex1.screen.data;

import io.jmix.ui.component.Component;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Department;
import ui.ex1.entity.Employee;

import java.util.List;

@UiController("uiex1_DepartmentData.browse")
@UiDescriptor("department-browse-data.xml")
@LookupComponent("departmentsTable")
public class DepartmentBrowseData extends StandardLookup<Department> {

    // tag::loader[]
    @Autowired
    private CollectionLoader<Department> departmentsDl;

    // end::loader[]

    // tag::name[]
    private String departmentName;

    // end::name[]

    // tag::before-show[]
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        departmentsDl.setParameter("name", departmentName);
        departmentsDl.load();
    }
    // end::before-show[]

    // tag::sample-method[]
    private void sampleMethod(Screen sampleScreen) {
        DataContext dataContext = UiControllerUtils.getScreenData(sampleScreen).getDataContext();
        // ...
    }
    // end::sample-method[]

}