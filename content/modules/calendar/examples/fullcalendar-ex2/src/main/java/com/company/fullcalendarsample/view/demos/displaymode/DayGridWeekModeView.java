package com.company.fullcalendarsample.view.demos.displaymode;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "day-grid-week-mode-view", layout = MainView.class)
@ViewController(id = "DayGridWeekModeView")
@ViewDescriptor(path = "day-grid-week-mode-view.xml")
public class DayGridWeekModeView extends StandardView {
}