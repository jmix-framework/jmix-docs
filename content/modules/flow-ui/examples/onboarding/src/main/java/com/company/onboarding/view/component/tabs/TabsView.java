package com.company.onboarding.view.component.tabs;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "TabsView", layout = MainView.class)
@ViewController("TabsView")
@ViewDescriptor("tabs-view.xml")
public class TabsView extends StandardView {

    // tag::content[]
    @ViewComponent
    private VerticalLayout content;

    // end::content[]

    @Autowired
    private UiComponents uiComponents;

    // tag::SelectedChangeEvent[]
    @Subscribe("tabs")
    public void onTabsSelectedChange(final Tabs.SelectedChangeEvent event) {
        setTabContent(event.getSelectedTab());
    }
    // end::SelectedChangeEvent[]

    // tag::SetTabContent[]
    private void setTabContent(Tab tab) {
        content.removeAll();
        Div tabLabel = uiComponents.create(Div.class);
        if ("tab1".equals(tab.getId().orElse(null))) {
            tabLabel.setText("Tab One is selected");
            content.add(tabLabel);
        } else {
            tabLabel.setText("Tab Two is selected");
            content.add(tabLabel);
        }
    }
    // end::SetTabContent[]
}