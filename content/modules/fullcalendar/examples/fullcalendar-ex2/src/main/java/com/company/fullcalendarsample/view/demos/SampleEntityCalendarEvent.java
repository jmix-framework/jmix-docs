package com.company.fullcalendarsample.view.demos;


import com.company.fullcalendarsample.entity.Event;
import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.fullcalendarflowui.component.data.EntityCalendarEvent;
import io.jmix.fullcalendarflowui.component.event.EventClickEvent;

@Route(value = "sample-entity-calendar-event", layout = MainView.class)
@ViewController("SampleEntityCalendarEvent")
@ViewDescriptor("sample-entity-calendar-event.xml")
public class SampleEntityCalendarEvent extends StandardView {

    // tag::sample-entity-calendar-event2[]
    @Subscribe("calendar")
    public void onCalendarEventClick(final EventClickEvent event) {
        EntityCalendarEvent<Event> entityEvent = event.getCalendarEvent();
        Event entity = entityEvent.getEntity();

    }
    // end::sample-entity-calendar-event2[]

}