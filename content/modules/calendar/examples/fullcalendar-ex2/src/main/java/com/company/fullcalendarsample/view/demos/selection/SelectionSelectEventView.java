package com.company.fullcalendarsample.view.demos.selection;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.fullcalendarflowui.component.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "selection-select-event-view", layout = MainView.class)
@ViewController(id = "SelectionSelectEventView")
@ViewDescriptor(path = "selection-select-event-view.xml")
public class SelectionSelectEventView extends StandardView {
    // tag::selection-select-event1[]
    @Autowired
    private Notifications notifications;

    @Subscribe("calendar")
    public void onCalendarSelect(final SelectEvent event) {
        notifications.show(String.format("Selected dates: from %s to %s",
                event.getStartDateTime(), event.getEndDateTime()));
    }
    // end::selection-select-event1[]
}