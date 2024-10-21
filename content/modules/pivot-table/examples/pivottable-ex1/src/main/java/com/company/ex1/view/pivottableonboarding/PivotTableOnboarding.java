package com.company.ex1.view.pivottableonboarding;


import com.company.ex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "pivot-table-onboarding", layout = MainView.class)
@ViewController(id = "PivotTableOnboarding")
@ViewDescriptor(path = "pivot-table-onboarding.xml")
public class PivotTableOnboarding extends StandardView {
}