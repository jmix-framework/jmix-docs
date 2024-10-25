package com.company.fullcalendarsample.view.demos.selection;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Route(value = "selection-programmatic-selection-control-view", layout = MainView.class)
@ViewController(id = "SelectionProgrammaticSelectionControlView")
@ViewDescriptor(path = "selection-programmatic-selection-control-view.xml")
public class SelectionProgrammaticSelectionControlView extends StandardView {

    // tag::selection-programmatic-selection-control1[]
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        calendar.select(LocalDate.now(), LocalDate.now().plusDays(2));
    }
    // end::selection-programmatic-selection-control1[]
}