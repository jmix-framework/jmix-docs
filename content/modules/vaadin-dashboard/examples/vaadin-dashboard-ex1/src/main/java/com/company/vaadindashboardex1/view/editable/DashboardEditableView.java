package com.company.vaadindashboardex1.view.editable;

import com.company.vaadindashboardex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.dashboard.component.JmixDashboard;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "dashboard-editable-view", layout = MainView.class)
@ViewController(id = "DashboardEditableView")
@ViewDescriptor(path = "dashboard-editable-view.xml")
public class DashboardEditableView extends StandardView {

    // tag::toggle[]
    @ViewComponent
    private JmixDashboard dashboard;

    @Subscribe("customizeBtn")
    public void onCustomizeBtnClick(final ClickEvent<JmixButton> event) {
        dashboard.setEditable(!dashboard.isEditable());
    }
    // end::toggle[]
}
