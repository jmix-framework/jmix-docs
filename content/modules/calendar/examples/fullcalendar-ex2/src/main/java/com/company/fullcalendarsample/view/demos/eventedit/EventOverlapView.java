package com.company.fullcalendarsample.view.demos.eventedit;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.component.data.ListCalendarDataProvider;
import io.jmix.fullcalendarflowui.component.data.SimpleCalendarEvent;
import io.jmix.fullcalendarflowui.kit.component.model.JsFunction;
import org.apache.groovy.util.Maps;

import java.time.LocalDateTime;
import java.util.List;

@Route(value = "event-overlap-view", layout = MainView.class)
@ViewController(id = "EventOverlapView")
@ViewDescriptor(path = "event-overlap-view.xml")
public class EventOverlapView extends StandardView {
    // tag::event-edit-event-overlap1[]
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        calendar.setEventOverlapJsFunction(new JsFunction("""
                function(stillEvent, movingEvent) {
                    return stillEvent.allDay && movingEvent.allDay;
                }"""));
    }
    // end::event-edit-event-overlap1[]

    @Subscribe
    public void onReady(final ReadyEvent event) {
        SimpleCalendarEvent calendarEvent1 = SimpleCalendarEvent.create()
                .withTitle("All-day event 1")
                .withAllDay(true)
                .withAdditionalProperties(Maps.of("eventStatus", "NEW"))
                .withStartDateTime(LocalDateTime.now())
                .build();
        SimpleCalendarEvent calendarEvent2 = SimpleCalendarEvent.create()
                .withTitle("All-day event 2")
                .withAllDay(true)
                .withAdditionalProperties(Maps.of("eventStatus", "NEXT"))
                .withStartDateTime(LocalDateTime.now().plusDays(1))
                .build();
        SimpleCalendarEvent calendarEvent3 = SimpleCalendarEvent.create()
                .withTitle("Simple event")
                .withAdditionalProperties(Maps.of("eventStatus", "INACTIVE"))
                .withStartDateTime(LocalDateTime.now().minusDays(1))
                .build();
        calendar.addDataProvider(new ListCalendarDataProvider(List.of(calendarEvent1, calendarEvent2, calendarEvent3)));
    }
}