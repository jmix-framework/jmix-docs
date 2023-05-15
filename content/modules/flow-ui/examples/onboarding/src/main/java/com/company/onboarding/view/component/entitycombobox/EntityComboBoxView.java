package com.company.onboarding.view.component.entitycombobox;


import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "EntityComboBoxView", layout = MainView.class)
@ViewController("EntityComboBoxView")
@ViewDescriptor("entity-combobox-view.xml")
public class EntityComboBoxView extends StandardView {

    // tag::departmentsDc[]
    @ViewComponent
    private CollectionContainer<Department> departmentsDc;

    // end::departmentsDc[]
    // tag::departmentField[]
    @ViewComponent
    private EntityComboBox<Department> departmentField;

    // end::departmentField[]
    // tag::dataManager[]
    @Autowired
    private DataManager dataManager;

    // end::dataManager[]
    // tag::filterEntityComboBox[]
    @ViewComponent
    private EntityComboBox<Department> filterEntityComboBox;

    // end::filterEntityComboBox[]
    // tag::CustomValueSetEvent[]
    @Subscribe("departmentField")
    public void onDepartmentFieldCustomValueSet(ComboBoxBase.CustomValueSetEvent
                                                            <ComboBox<Department>> event) {
        Department department = dataManager.create(Department.class); // <1>
        department.setName(event.getDetail()); // <2>
        departmentsDc.getMutableItems().add(department); // <3>
        departmentField.setValue(department);
    }
    // end::CustomValueSetEvent[]
    // tag::filter[]
    @Subscribe
    public void onInit(InitEvent event) {
        ComboBox.ItemFilter<Department> filter = (department, filterString) -> department
                .getName().toLowerCase().startsWith(filterString.toLowerCase());
        filterEntityComboBox.setItems(departmentsDc,filter);
    }
    // end::filter[]
}