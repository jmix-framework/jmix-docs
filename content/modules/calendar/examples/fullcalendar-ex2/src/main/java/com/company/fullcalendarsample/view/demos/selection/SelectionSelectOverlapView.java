package com.company.fullcalendarsample.view.demos.selection;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.component.data.ListCalendarDataProvider;
import io.jmix.fullcalendarflowui.component.data.SimpleCalendarEvent;
import io.jmix.fullcalendarflowui.component.model.Display;
import io.jmix.fullcalendarflowui.kit.component.model.JsFunction;

import java.time.LocalDateTime;
import java.util.List;

@Route(value = "selection-select-overlap-view", layout = MainView.class)
@ViewController(id = "SelectionSelectOverlapView")
@ViewDescriptor(path = "selection-select-overlap-view.xml")
public class SelectionSelectOverlapView extends StandardView {
    // tag::selection-select-overlap1[]
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        calendar.setSelectOverlapJsFunction(new JsFunction("""
                function(event) {
                    return event.display === 'background';
                }"""));
    }
    // end::selection-select-overlap1[]

    @Subscribe
    public void onReady(final ReadyEvent event) {
        SimpleCalendarEvent calendarEvent1 = SimpleCalendarEvent.create()
                .withTitle("Simple event")
                .withStartDateTime(LocalDateTime.now().minusDays(2))
                .build();
        SimpleCalendarEvent calendarEvent2 = SimpleCalendarEvent.create()
                .withTitle("BG event 1")
                .withAllDay(true)
                .withDisplay(Display.BACKGROUND)
                .withStartDateTime(LocalDateTime.now().plusDays(1))
                .build();
        SimpleCalendarEvent calendarEvent3 = SimpleCalendarEvent.create()
                .withTitle("BG event 2")
                .withAllDay(true)
                .withDisplay(Display.BACKGROUND)
                .withStartDateTime(LocalDateTime.now().plusDays(2))
                .build();
        calendar.addDataProvider(new ListCalendarDataProvider(List.of(calendarEvent1, calendarEvent2, calendarEvent3)));
    }
}