package com.company.fullcalendarsample.view.demos;


import com.company.fullcalendarsample.entity.Event;
import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.event.EventDropEvent;

@Route(value = "sample-recurring-events-view", layout = MainView.class)
@ViewController(id = "SampleRecurringEventsView")
@ViewDescriptor(path = "sample-recurring-events-view.xml")
public class SampleRecurringEventsView extends StandardView {
    @ViewComponent
    private CollectionContainer<Event> eventsDc;

    @Subscribe("calendar")
    public void onCalendarEventDrop(final EventDropEvent event) {
        System.out.println();
    }
}