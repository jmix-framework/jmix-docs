package com.company.supersetsample.view.samples.dashboard;


import com.company.supersetsample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.supersetflowui.component.SupersetDashboard;

@Route(value = "dashboard-view", layout = MainView.class)
@ViewController("DashboardView")
@ViewDescriptor("dashboard-view.xml")
public class DashboardView extends StandardView {
    // tag::setting-embedded-id[]
    @ViewComponent
    private SupersetDashboard dashboard;

    @Subscribe
    public void onInit(final InitEvent event) {
        dashboard.setEmbeddedId("1aec5c74-f143-4051-818b-fcf9d77c8501");
    }
    // end::setting-embedded-id[]
}