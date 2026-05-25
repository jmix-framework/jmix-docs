package com.company.fullcalendarsample.view.demos.displaymode;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.component.data.ListCalendarDataProvider;
import io.jmix.fullcalendarflowui.component.data.SimpleCalendarEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Route(value = "time-grid-week-mode-view", layout = MainView.class)
@ViewController(id = "TimeGridWeekModeView")
@ViewDescriptor(path = "time-grid-week-mode-view.xml")
public class TimeGridWeekModeView extends StandardView {
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        SimpleCalendarEvent event1 = SimpleCalendarEvent.create()
                .withTitle("Car service")
                .withStartDateTime(LocalDate.now(), LocalTime.of(19, 0))
                .withEndDateTime(LocalDate.now(), LocalTime.of(21, 30))
                .build();
        SimpleCalendarEvent event2 = SimpleCalendarEvent.create()
                .withTitle("Demonstration")
                .withStartDateTime(LocalDate.now().plusDays(1), LocalTime.of(12, 30))
                .withEndDateTime(LocalDate.now().plusDays(1), LocalTime.of(15, 0))
                .build();
        SimpleCalendarEvent event3 = SimpleCalendarEvent.create()
                .withTitle("Inventory")
                .withAllDay(true)
                .withStartDateTime(LocalDate.now().plusDays(2), LocalTime.of(8, 0))
                .withEndDateTime(LocalDate.now().plusDays(4), LocalTime.of(17, 0))
                .build();
        SimpleCalendarEvent event4 = SimpleCalendarEvent.create()
                .withTitle("Meeting")
                .withStartDateTime(LocalDate.now().plusDays(4), LocalTime.of(13, 0))
                .withEndDateTime(LocalDate.now().plusDays(4), LocalTime.of(15, 0))
                .build();

        calendar.addDataProvider(new ListCalendarDataProvider(List.of(event1, event2, event3, event4)));
    }
}