package com.company.fullcalendarsample.view.demos.contextmenu;


import com.company.fullcalendarsample.entity.Event;
import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.component.contextmenu.FullCalendarContextMenu;
import io.jmix.fullcalendarflowui.component.data.CalendarEvent;
import io.jmix.fullcalendarflowui.component.data.EntityCalendarEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Route(value = "context-menu-menu-content-view", layout = MainView.class)
@ViewController(id = "ContextMenuMenuContentView")
@ViewDescriptor(path = "context-menu-menu-content-view.xml")
public class ContextMenuMenuContentView extends StandardView {
    // tag::context-menu-menu-context1[]
    @ViewComponent
    private CollectionContainer<Event> eventsDc;
    @ViewComponent
    private FullCalendar calendar;

    @Autowired
    private DialogWindows dialogWindows;

    @Subscribe
    public void onInit(final InitEvent event) {
        FullCalendarContextMenu contextMenu = calendar.getContextMenu(); // <1>
        contextMenu.setContentMenuHandler(context -> { // <2>
            EntityCalendarEvent<Event> calendarEvent = context.getCalendarEvent();
            if (calendarEvent != null) {
                contextMenu.removeAll(); // <3>

                contextMenu.addItem("Edit", clickEvent -> { // <4>
                    dialogWindows.detail(this, Event.class)
                            .editEntity(calendarEvent.getEntity())
                            .open();
                });
                contextMenu.addItem("Remove", clickEvent -> {
                    eventsDc.getMutableItems().remove(calendarEvent.getEntity());
                });

                return true; // <5>
            }
            return false;
        });
    }
    // end::context-menu-menu-context1[]
}