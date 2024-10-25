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
import io.jmix.fullcalendarflowui.component.event.EventDropEvent;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "event-drag-and-drop-view", layout = MainView.class)
@ViewController(id = "EventDragAndDropView")
@ViewDescriptor(path = "event-drag-and-drop-view.xml")
public class EventDragAndDropView extends StandardView {
    // tag::event-edit-drag-and-drop2[]
    @Autowired
    private DataManager dataManager;

    @Subscribe("calendar")
    public void onCalendarEventDrop(final EventDropEvent event) {
        EntityCalendarEvent<Event> calendarEvent = event.getCalendarEvent();

        dataManager.save(calendarEvent.getEntity());
    }
    // end::event-edit-drag-and-drop2[]
}