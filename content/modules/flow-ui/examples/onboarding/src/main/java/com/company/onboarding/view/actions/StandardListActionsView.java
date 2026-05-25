package com.company.onboarding.view.actions;


import com.company.onboarding.entity.Department;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.list.EditAction;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "StandardListActionsView", layout = MainView.class)
@ViewController("StandardListActionsView")
@ViewDescriptor("standard-list-actions-view.xml")
public class StandardListActionsView extends StandardView {

    // tag::properties[]
    @ViewComponent("myDepartmentsTable.edit")
    private EditAction<Department> myDepartmentsTableEdit;

    @Subscribe
    public void onInit(final InitEvent event) {
        myDepartmentsTableEdit.setViewId("CustomDepartmentDetailView");
    }
    // end::properties[]

    @Autowired
    private Notifications notifications;

    // tag::handlers[]
    @Install(to = "departmentsTable.create", subject = "afterSaveHandler")
    private void departmentsTableCreateAfterSaveHandler(final Department department) {
        notifications.create("Created " + department).show();
    }
    // end::handlers[]
}