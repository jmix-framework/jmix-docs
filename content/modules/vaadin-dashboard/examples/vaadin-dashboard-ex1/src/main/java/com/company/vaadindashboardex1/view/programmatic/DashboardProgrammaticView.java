package com.company.vaadindashboardex1.view.programmatic;

import com.company.vaadindashboardex1.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.dashboard.DashboardSection;
import com.vaadin.flow.component.dashboard.DashboardWidget;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.Route;
import io.jmix.dashboard.component.JmixDashboard;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "dashboard-programmatic-view", layout = MainView.class)
@ViewController(id = "DashboardProgrammaticView")
@ViewDescriptor(path = "dashboard-programmatic-view.xml")
public class DashboardProgrammaticView extends StandardView {

    @ViewComponent
    private JmixDashboard dashboard;

    // tag::create-widget[]
    @Autowired
    private UiComponents uiComponents;

    private DashboardWidget createWidget(String title, String value) {
        DashboardWidget widget = uiComponents.create(DashboardWidget.class);
        widget.setTitle(title);

        H3 valueLabel = uiComponents.create(H3.class);
        valueLabel.setText(value);
        widget.setContent(valueLabel); // a widget holds one component; use a layout for several

        return widget;
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        dashboard.add(createWidget("Revenue", "1,204,500"),
                createWidget("Orders", "3,182"));
    }
    // end::create-widget[]

    @Subscribe("addWidgetBtn")
    public void onAddWidgetBtnClick(final ClickEvent<JmixButton> event) {
        // tag::add-remove[]
        DashboardWidget widget = createWidget("Visitors", "18,420");
        dashboard.addWidgetAtIndex(0, widget);
        // end::add-remove[]
    }

    @Subscribe("addSectionBtn")
    public void onAddSectionBtnClick(final ClickEvent<JmixButton> event) {
        // tag::create-section[]
        DashboardSection regionsSection = dashboard.addSection("Regions");
        regionsSection.add(createWidget("North", "612,100"),
                createWidget("South", "592,400"));
        // end::create-section[]
    }

    @Subscribe("clearBtn")
    public void onClearBtnClick(final ClickEvent<JmixButton> event) {
        // tag::remove-all[]
        dashboard.removeAll();
        // end::remove-all[]
    }
}
