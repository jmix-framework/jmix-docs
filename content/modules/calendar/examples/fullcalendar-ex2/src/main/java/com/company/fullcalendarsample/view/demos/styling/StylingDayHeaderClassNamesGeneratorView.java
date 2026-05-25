package com.company.fullcalendarsample.view.demos.styling;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.Install;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.fullcalendarflowui.component.event.DayHeaderClassNamesContext;
import io.jmix.fullcalendarflowui.component.model.DayOfWeek;

import java.util.List;

@Route(value = "styling-day-header-class-names-generator-view", layout = MainView.class)
@ViewController(id = "StylingDayHeaderClassNamesGeneratorView")
@ViewDescriptor(path = "styling-day-header-class-names-generator-view.xml")
public class StylingDayHeaderClassNamesGeneratorView extends StandardView {

    // tag::styling-day-header-class-names-generator1[]
    @Install(to = "calendar", subject = "dayHeaderClassNamesGenerator")
    private List<String> calendarDayHeaderClassNamesGenerator(final DayHeaderClassNamesContext context) {
        if (context.getDayOfWeek() == DayOfWeek.SATURDAY
                || context.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return List.of("weekend");
        }
        return null;
    }
    // end::styling-day-header-class-names-generator1[]
}