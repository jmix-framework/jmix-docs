package com.company.onboarding.view.pivottable;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "pivottable-view", layout = MainView.class)
@ViewController(id = "PivotTableView")
@ViewDescriptor(path = "pivottable-view.xml")
public class PivotTableView extends StandardView {
}