package com.company.onboarding.view.actions;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.action.ActionVariant;
import io.jmix.flowui.kit.action.BaseAction;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "ActionsView", layout = MainView.class)
@ViewController("ActionsView")
@ViewDescriptor("actions-view.xml")
public class ActionsView extends StandardView {

    @Autowired
    private Notifications notifications;

    // tag::action-inject[]
    @ViewComponent
    private BaseAction someAction;

    @ViewComponent("departmentsTable.sayHello")
    private BaseAction departmentsTableSayHello;

    @Subscribe
    public void onInit(final InitEvent event) {
        someAction.setEnabled(false);
        departmentsTableSayHello.setVariant(ActionVariant.SUCCESS);
    }
    // end::action-inject[]

    // tag::action[]
    @Subscribe("departmentsTable.sayHello")
    public void onDepartmentsTableSayHello(final ActionPerformedEvent event) {
        notifications.create("Hello").show();
    }
    // end::action[]

}