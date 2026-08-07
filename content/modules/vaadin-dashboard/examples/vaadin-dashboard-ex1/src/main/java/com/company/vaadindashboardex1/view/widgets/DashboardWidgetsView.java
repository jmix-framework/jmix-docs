package com.company.vaadindashboardex1.view.widgets;

import com.company.vaadindashboardex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.dashboard.DashboardWidget;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "dashboard-widgets-view", layout = MainView.class)
@ViewController(id = "DashboardWidgetsView")
@ViewDescriptor(path = "dashboard-widgets-view.xml")
public class DashboardWidgetsView extends StandardView {

    @ViewComponent
    private DashboardWidget ordersWidget;

    @ViewComponent
    private H3 ordersValue;

    @Subscribe("refreshOrdersBtn")
    public void onRefreshOrdersBtnClick(final ClickEvent<JmixButton> event) {
        ordersValue.setText("3,199");
        ordersWidget.setTitle("Orders (updated)");
    }
}
