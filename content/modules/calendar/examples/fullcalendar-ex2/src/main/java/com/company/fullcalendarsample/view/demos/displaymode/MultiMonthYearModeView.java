package com.company.fullcalendarsample.view.demos.displaymode;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "multi-month-year-mode-view", layout = MainView.class)
@ViewController(id = "MultiMonthYearModeView")
@ViewDescriptor(path = "multi-month-year-mode-view.xml")
public class MultiMonthYearModeView extends StandardView {
}