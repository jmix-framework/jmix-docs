package com.company.fullcalendarsample.view.demos.displaymode;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.kit.component.model.CustomCalendarDisplayMode;

@Route(value = "custom-display-mode-view", layout = MainView.class)
@ViewController(id = "CustomDisplayModeView")
@ViewDescriptor(path = "custom-display-mode-view.xml")
public class CustomDisplayModeView extends StandardView {
    // tag::custom-display-mode3[]
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        calendar.addCustomCalendarDisplayMode(
                new CustomCalendarDisplayMode("dayGridTwoDays")
                        .withDayCount(2));

        calendar.setInitialCalendarDisplayMode(() -> "dayGridTwoDays");
    }
    // end::custom-display-mode3[]
}