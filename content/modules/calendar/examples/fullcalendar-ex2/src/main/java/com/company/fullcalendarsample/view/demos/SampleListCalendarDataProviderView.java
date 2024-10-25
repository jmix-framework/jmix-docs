package com.company.fullcalendarsample.view.demos;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.component.data.ListCalendarDataProvider;
import io.jmix.fullcalendarflowui.component.data.SimpleCalendarEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Route(value = "sample-list-calendar-data-provider-view", layout = MainView.class)
@ViewController(id = "SampleListCalendarDataProviderView")
@ViewDescriptor(path = "sample-list-calendar-data-provider-view.xml")
public class SampleListCalendarDataProviderView extends StandardView {
    // tag::sample-list-calendar-data-provider1[]
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        SimpleCalendarEvent calendarEvent = SimpleCalendarEvent.create()
                .withTitle("Morning jog")
                .withStartDateTime(LocalDate.now(), LocalTime.of(8, 0))
                .build();

        ListCalendarDataProvider listDataProvider =
                new ListCalendarDataProvider(List.of(calendarEvent));

        calendar.addDataProvider(listDataProvider);
    }
    // end::sample-list-calendar-data-provider1[]
}