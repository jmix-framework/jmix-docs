package com.company.fullcalendarsample.view.demos;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "sample-container-calendar-data-provider-view", layout = MainView.class)
@ViewController(id = "SampleContainerCalendarDataProviderView")
@ViewDescriptor(path = "sample-container-calendar-data-provider-view.xml")
public class SampleContainerCalendarDataProviderView extends StandardView {
}