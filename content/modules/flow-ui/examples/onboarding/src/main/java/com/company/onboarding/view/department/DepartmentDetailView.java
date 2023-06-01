package com.company.onboarding.view.department;

import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;

// tag::annotations[]
// common annotations
@ViewController("Department.detail")
@ViewDescriptor("department-detail-view.xml")
@Route(value = "departments/:id", layout = MainView.class)
// detail view annotations
@EditedEntityContainer("departmentDc")
@PrimaryDetailView(Department.class)
public class DepartmentDetailView extends StandardDetailView<Department> {
// end::annotations[]

    // tag::ItemPropertyChangeEvent[]
    @Subscribe(id = "departmentDc", target = Target.DATA_CONTAINER)
    public void onDepartmentDcItemPropertyChange(
            final InstanceContainer.ItemPropertyChangeEvent<Department> event) {
        Department department = event.getItem();
        String changedProperty = event.getProperty();
        Object currentValue = event.getValue();
        Object previousValue = event.getPrevValue();
        // ...
    }
    // end::ItemPropertyChangeEvent[]

    // tag::ItemChangeEvent[]
    @Subscribe(id = "departmentDc", target = Target.DATA_CONTAINER)
    public void onDepartmentDcItemChange(final InstanceContainer.ItemChangeEvent<Department> event) {
        Department department = event.getItem();
        Department previouslySelectedDepartment = event.getPrevItem();
        // ...
    }
    // end::ItemChangeEvent[]
}