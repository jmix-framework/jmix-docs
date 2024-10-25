package com.company.fullcalendarsample.view.demos.displaymode;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;

import java.time.LocalDate;

@Route(value = "visible-range-view", layout = MainView.class)
@ViewController(id = "VisibleRangeView")
@ViewDescriptor(path = "visible-range-view.xml")
public class VisibleRangeView extends StandardView {
    // tag::visible-range2[]
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        calendar.setVisibleRange(
                LocalDate.now(), LocalDate.now().plusDays(3));
    }
    // end::visible-range2[]
}