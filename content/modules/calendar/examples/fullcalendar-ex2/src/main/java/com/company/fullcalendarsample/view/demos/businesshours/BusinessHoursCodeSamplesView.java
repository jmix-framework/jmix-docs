package com.company.fullcalendarsample.view.demos.businesshours;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "business-hours-code-samples-view", layout = MainView.class)
@ViewController(id = "BusinessHoursCodeSamplesView")
@ViewDescriptor(path = "business-hours-code-samples-view.xml")
public class BusinessHoursCodeSamplesView extends StandardView {
}