package com.company.ex1.view.pivottablesettings;


import com.company.ex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "pivottable-settings-view", layout = MainView.class)
@ViewController(id = "PivottableSettingsView")
@ViewDescriptor(path = "pivottable-settings-view.xml")
public class PivottableSettingsView extends StandardView {
}