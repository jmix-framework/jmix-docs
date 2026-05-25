package com.company.fullcalendarsample.view.demos.selection;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "selection-code-samples-view", layout = MainView.class)
@ViewController(id = "SelectionCodeSamplesView")
@ViewDescriptor(path = "selection-code-samples-view.xml")
public class SelectionCodeSamplesView extends StandardView {
}