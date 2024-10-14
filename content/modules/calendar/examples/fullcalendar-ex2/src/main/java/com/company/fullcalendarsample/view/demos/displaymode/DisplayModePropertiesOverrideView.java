package com.company.fullcalendarsample.view.demos.displaymode;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "display-mode-properties-override-view", layout = MainView.class)
@ViewController(id = "DisplayModePropertiesOverrideView")
@ViewDescriptor(path = "display-mode-properties-override-view.xml")
public class DisplayModePropertiesOverrideView extends StandardView {
}