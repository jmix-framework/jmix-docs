package com.company.fullcalendarsample.view.demos.eventedit;


import com.company.fullcalendarsample.entity.Event;
import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.fullcalendarflowui.component.data.EntityCalendarEvent;
import io.jmix.fullcalendarflowui.component.event.EventResizeEvent;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "event-resize-view", layout = MainView.class)
@ViewController(id = "EventResizeView")
@ViewDescriptor(path = "event-resize-view.xml")
public class EventResizeView extends StandardView {
    // tag::event-edit-resizable2[]
    @Autowired
    private DataManager dataManager;

    @Subscribe("calendar")
    public void onCalendarEventResize(final EventResizeEvent event) {
        EntityCalendarEvent<Event> calendarEvent = event.getCalendarEvent();

        dataManager.save(calendarEvent);
    }
    // end::event-edit-resizable2[]
}