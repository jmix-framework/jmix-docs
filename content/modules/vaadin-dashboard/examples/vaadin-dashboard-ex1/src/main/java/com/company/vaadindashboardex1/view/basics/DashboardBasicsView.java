package com.company.vaadindashboardex1.view.basics;

import com.company.vaadindashboardex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "dashboard-basics-view", layout = MainView.class)
@ViewController(id = "DashboardBasicsView")
@ViewDescriptor(path = "dashboard-basics-view.xml")
public class DashboardBasicsView extends StandardView {
}
