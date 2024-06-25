package com.company.onboarding.view.step;

import com.company.onboarding.entity.Step;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGridColumnVisibilityChangedEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "steps", layout = MainView.class)
@ViewController("Step.list")
@ViewDescriptor("step-list-view.xml")
@LookupComponent("stepsTable")
@DialogMode(width = "50em", height = "37.5em")
public class StepListView extends StandardListView<Step> {
    @Autowired
    private Notifications notifications;

    @Subscribe("stepsTable.name")
    public void onStepsTableNameDataGridColumnVisibilityChanged(final DataGridColumnVisibilityChangedEvent<Step> event) {
        notifications
                .show("Visible: " + event.isVisible());
    }
}