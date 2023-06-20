package com.company.onboarding.view.component.entitypicker;


import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Actions;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.entitypicker.EntityOpenAction;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.action.BaseAction;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "EntityPickerView", layout = MainView.class)
@ViewController("EntityPickerView")
@ViewDescriptor("entity-picker-view.xml")
public class EntityPickerView extends StandardView {
    // tag::departmentEntityPicker[]
    @ViewComponent
    private EntityPicker<Department> departmentEntityPicker;

    // end::departmentEntityPicker[]
    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]
    // tag::actions[]
    @Autowired
    private Actions actions;

    // end::actions[]
    // tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit[]
        // tag::addAction[]
        departmentEntityPicker.addAction(actions.create(EntityOpenAction.class));
        // end::addAction[]
        // tag::addCustomAction[]
        departmentEntityPicker.addAction(new BaseAction("showManager")
                .withIcon(VaadinIcon.QUESTION_CIRCLE)
                .withHandler(e -> {
                    Department department = departmentEntityPicker.getValue();
                    if (department != null)
                        notifications.create(department.getName() + " has "
                                        + department.getHrManager() + " HR-manager")
                                .show();
                    else notifications.create("Choose a department").show();
                }));
        // end::addCustomAction[]
        // tag::onInit[]
    }
    // end::onInit[]
    // tag::onKnowManager[]
    @Subscribe("departmentEntityPicker.knowManager")
    public void onKnowManager(ActionPerformedEvent event) {
        Department department = departmentEntityPicker.getValue();
        if (department != null)
            notifications.create(department.getName() + " has "
                    + department.getHrManager() + " HR-manager")
                    .show();
        else notifications.create("Choose a department").show();
    }
    // end::onKnowManager[]
}