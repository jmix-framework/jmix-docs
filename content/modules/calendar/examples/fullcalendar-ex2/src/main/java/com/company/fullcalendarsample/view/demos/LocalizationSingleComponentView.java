package com.company.fullcalendarsample.view.demos;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.component.FullCalendarI18n;
import io.jmix.fullcalendarflowui.component.model.DayOfWeek;

@Route(value = "localization-single-component-view", layout = MainView.class)
@ViewController(id = "LocalizationSingleComponentView")
@ViewDescriptor(path = "localization-single-component-view.xml")
public class LocalizationSingleComponentView extends StandardView {
    // tag::localization-single-component2[]
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        calendar.setI18n(new FullCalendarI18n()
                .withFirstDayOfWeek(DayOfWeek.MONDAY)
                .withDirection(FullCalendarI18n.Direction.RTL));
    }
    // end::localization-single-component2[]
}