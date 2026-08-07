package com.company.vaadindashboardex1.view.grid;

import com.company.vaadindashboardex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.dashboard.component.JmixDashboard;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "dashboard-grid-view", layout = MainView.class)
@ViewController(id = "DashboardGridView")
@ViewDescriptor(path = "dashboard-grid-view.xml")
public class DashboardGridView extends StandardView {

    @ViewComponent
    private JmixDashboard dashboard;

    @Subscribe("compactBtn")
    public void onCompactBtnClick(final ClickEvent<JmixButton> event) {
        // tag::grid-java[]
        dashboard.setMaximumColumnCount(3);
        dashboard.setMinimumColumnWidth("18em");
        dashboard.setMaximumColumnWidth("32em");
        dashboard.setMinimumRowHeight("10em");
        dashboard.setGap("1em");
        dashboard.setPadding("1em");
        dashboard.setDenseLayout(true);
        // end::grid-java[]
    }
}
