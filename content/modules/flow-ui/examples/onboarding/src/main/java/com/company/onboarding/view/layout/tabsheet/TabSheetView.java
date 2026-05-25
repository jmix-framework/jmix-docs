package com.company.onboarding.view.layout.tabsheet;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "TabsheetView", layout = MainView.class)
@ViewController("TabsheetView")
@ViewDescriptor("tabsheet-view.xml")
public class TabSheetView extends StandardView {

    // tag::lazy-loading[]
    @ViewComponent
    private Span contentInfo;
    @ViewComponent
    private JmixTabSheet tabSheet;
    @Autowired
    private Notifications notifications;
    // end::lazy-loading[]

    // tag::onInit[]

    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::lazy-loading[]
        checkTabsContent();
        // end::lazy-loading[]
        // tag::onInit[]
    }
    // end::onInit[]

    // tag::change-event[]
    @Subscribe("tabSheet")
    public void onTabSheetSelectedChange(final JmixTabSheet.SelectedChangeEvent event) {
        // end::change-event[]
        // tag::lazy-loading[]
        checkTabsContent();
        // end::lazy-loading[]

        // tag::notify-on-switch[]
        notifications.create(event.getSelectedTab() + " is now selected").show();
        // end::notify-on-switch[]
        // tag::change-event[]
    }
    // end::change-event[]

    // tag::lazy-loading[]

    protected void checkTabsContent () {
        StringBuilder sb = new StringBuilder();
        List<Component> tab1Content = tabSheet.getContentByTab(tabSheet.getTabAt(0)).getChildren().toList();
        sb.append(tab1Content.isEmpty() ? "Empty" :  tab1Content.size() + " components");

        sb.append(" / ");
        List<Component> tab2Content = tabSheet.getContentByTab(tabSheet.getTabAt(1)).getChildren().toList();
        sb.append(tab2Content.isEmpty() ? "Empty" : tab2Content.size() + " components");

        contentInfo.setText(sb.toString());
    }
    //end::lazy-loading[]

}