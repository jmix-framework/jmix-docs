package com.company.fullcalendarsample.view.demos.selection;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.kit.component.model.JsFunction;

import java.time.LocalDate;
import java.util.TimeZone;

@Route(value = "selection-select-allow-function-view", layout = MainView.class)
@ViewController(id = "SelectionSelectAllowFunctionView")
@ViewDescriptor(path = "selection-select-allow-function-view.xml")
public class SelectionSelectAllowFunctionView extends StandardView {
    // tag::selection-select-allow-function1[]
    @ViewComponent
    private FullCalendar calendar;

    @Subscribe
    public void onInit(final InitEvent event) {
        calendar.setSelectAllowJsFunction(
                new JsFunction(String.format("""
                                function(selectionInfo) { // <1>
                                    return selectionInfo.start >= new Date('%s')
                                    && selectionInfo.end <= new Date('%s');
                                }""",
                        convertToStrDate(LocalDate.now().minusDays(1)), // <2>
                        convertToStrDate(LocalDate.now().plusDays(2)))));
    }

    protected String convertToStrDate(LocalDate date) { // <3>
        TimeZone timeZone = calendar.getTimeZone() == null
                ? TimeZone.getDefault()
                : calendar.getTimeZone();
        return date.atStartOfDay(timeZone.toZoneId()).toOffsetDateTime().toString();
    }
    // end::selection-select-allow-function1[]
}