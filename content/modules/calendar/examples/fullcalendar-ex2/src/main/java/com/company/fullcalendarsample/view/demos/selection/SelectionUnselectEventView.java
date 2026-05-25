package com.company.fullcalendarsample.view.demos.selection;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.fullcalendarflowui.component.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "selection-unselect-event-view", layout = MainView.class)
@ViewController(id = "SelectionUnselectEventView")
@ViewDescriptor(path = "selection-unselect-event-view.xml")
public class SelectionUnselectEventView extends StandardView {
    // tag::selection-unselect-event1[]
    @Autowired
    private Notifications notifications;

    @Subscribe("calendar")
    public void onCalendarUnselect(final UnselectEvent event) {
        notifications.show("Selection is cleared");
    }
    // end::selection-unselect-event1[]
}