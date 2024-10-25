package com.company.fullcalendarsample.view.demos;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.component.data.ListCalendarDataProvider;
import io.jmix.fullcalendarflowui.component.data.SimpleCalendarEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Route(value = "sample-simple-calendar-event-view", layout = MainView.class)
@ViewController(id = "SampleSimpleCalendarEventView")
@ViewDescriptor(path = "sample-simple-calendar-event-view.xml")
public class SampleSimpleCalendarEventView extends StandardView {

    // tag::sample-simple-calendar-event1[]
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        SimpleCalendarEvent calendarEvent = SimpleCalendarEvent.create()
                .withTitle("Car service")
                .withStartDateTime(LocalDateTime.now())
                .build();

        calendar.addDataProvider(new ListCalendarDataProvider(List.of(calendarEvent)));
    }
    // end::sample-simple-calendar-event1[]

}