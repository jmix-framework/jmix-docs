package com.company.fullcalendarsample.view.demos.styling;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.component.data.CalendarEvent;
import io.jmix.fullcalendarflowui.component.data.ListCalendarDataProvider;
import io.jmix.fullcalendarflowui.component.data.SimpleCalendarEvent;

import java.time.LocalDateTime;
import java.util.List;

@Route(value = "styling-properties-view", layout = MainView.class)
@ViewController(id = "StylingPropertiesView")
@ViewDescriptor(path = "styling-properties-view.xml")
public class StylingPropertiesView extends StandardView {
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::styling-properties2[]
        CalendarEvent calendarEvent = SimpleCalendarEvent.create()
                .withTitle("Colored event")
                .withAllDay(true)
                .withStartDateTime(LocalDateTime.now())
                .withTextColor("#80EA69")
                .withBorderColor("#A6A200")
                .withBackgroundColor("#188A00")
                .build();
        // end::styling-properties2[]

        // tag::styling-properties3[]
        CalendarEvent redCalendarEvent = SimpleCalendarEvent.create()
                .withTitle("Colored event")
                .withStartDateTime(LocalDateTime.now())
                .withClassNames("red-text-event")
                .build();
        // end::styling-properties3[]

        calendar.addDataProvider(new ListCalendarDataProvider(List.of(calendarEvent, redCalendarEvent)));
    }
}