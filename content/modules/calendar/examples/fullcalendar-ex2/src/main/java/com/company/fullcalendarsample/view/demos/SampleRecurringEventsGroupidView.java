package com.company.fullcalendarsample.view.demos;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "sample-recurring-events-groupid-view", layout = MainView.class)
@ViewController(id = "SampleRecurringEventsGroupidView")
@ViewDescriptor(path = "sample-recurring-events-groupid-view.xml")
public class SampleRecurringEventsGroupidView extends StandardView {
}