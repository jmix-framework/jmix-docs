package com.company.fullcalendarsample.view.demos.displaymode;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "display-mode-properties-view", layout = MainView.class)
@ViewController(id = "DisplayModePropertiesView")
@ViewDescriptor(path = "display-mode-properties-view.xml")
public class DisplayModePropertiesView extends StandardView {
}