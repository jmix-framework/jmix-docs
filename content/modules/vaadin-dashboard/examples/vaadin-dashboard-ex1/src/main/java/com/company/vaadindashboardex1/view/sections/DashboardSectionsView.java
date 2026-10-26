package com.company.vaadindashboardex1.view.sections;

import com.company.vaadindashboardex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "dashboard-sections-view", layout = MainView.class)
@ViewController(id = "DashboardSectionsView")
@ViewDescriptor(path = "dashboard-sections-view.xml")
public class DashboardSectionsView extends StandardView {
}
