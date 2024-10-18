package com.company.onboarding.view.pivottablecustomdatabinding;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "pivot-table-custom-data-binding-view", layout = MainView.class)
@ViewController(id = "PivotTableCustomDataBindingView")
@ViewDescriptor(path = "pivot-table-custom-data-binding-view.xml")
public class PivotTableCustomDataBindingView extends StandardView {
}