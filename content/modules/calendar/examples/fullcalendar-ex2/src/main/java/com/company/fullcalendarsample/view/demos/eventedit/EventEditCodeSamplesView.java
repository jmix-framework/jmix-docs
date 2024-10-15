package com.company.fullcalendarsample.view.demos.eventedit;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "event-edit-code-samples-view", layout = MainView.class)
@ViewController(id = "EventEditCodeSamplesView")
@ViewDescriptor(path = "event-edit-code-samples-view.xml")
public class EventEditCodeSamplesView extends StandardView {
}