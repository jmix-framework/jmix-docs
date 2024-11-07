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

@Route(value = "day-grid-day-mode-view", layout = MainView.class)
@ViewController(id = "DayGridDayModeView")
@ViewDescriptor(path = "day-grid-day-mode-view.xml")
public class DayGridDayModeView extends StandardView {
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        SimpleCalendarEvent event1 = SimpleCalendarEvent.create()
                .withTitle("Morning jog")
                .withStartDateTime(LocalDate.now(), LocalTime.of(7, 0))
                .withEndDateTime(LocalDate.now(), LocalTime.of(7, 30))
                .build();
        SimpleCalendarEvent event2 = SimpleCalendarEvent.create()
                .withTitle("Breakfast")
                .withStartDateTime(LocalDate.now(), LocalTime.of(7, 30))
                .withEndDateTime(LocalDate.now(), LocalTime.of(8, 0))
                .build();
        SimpleCalendarEvent event3 = SimpleCalendarEvent.create()
                .withTitle("Work hours")
                .withStartDateTime(LocalDate.now(), LocalTime.of(8, 0))
                .withEndDateTime(LocalDate.now(), LocalTime.of(17, 0))
                .build();
        SimpleCalendarEvent event4 = SimpleCalendarEvent.create()
                .withTitle("Cinema")
                .withStartDateTime(LocalDate.now(), LocalTime.of(18, 0))
                .withEndDateTime(LocalDate.now(), LocalTime.of(20, 0))
                .build();
        calendar.addDataProvider(new ListCalendarDataProvider(List.of(event1, event2, event3, event4)));
    }


}