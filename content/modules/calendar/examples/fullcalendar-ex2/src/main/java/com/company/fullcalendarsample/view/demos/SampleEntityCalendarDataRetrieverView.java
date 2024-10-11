package com.company.fullcalendarsample.view.demos;


import com.company.fullcalendarsample.services.EventService;
import com.company.fullcalendarsample.services.TaskEventService;
import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.fullcalendarflowui.component.FullCalendar;
import io.jmix.fullcalendarflowui.component.data.CalendarDataRetriever;
import io.jmix.fullcalendarflowui.component.data.CalendarEvent;
import io.jmix.fullcalendarflowui.component.data.CallbackCalendarDataProvider.ItemsFetchContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "sample-entity-calendar-data-retriever-view", layout = MainView.class)
@ViewController(id = "SampleEntityCalendarDataRetrieverView")
@ViewDescriptor(path = "sample-entity-calendar-data-retriever-view.xml")
public class SampleEntityCalendarDataRetrieverView extends StandardView {
    // tag::sample-entity-calendar-data-retriever4[]
    @Autowired
    private EventService eventService;

    @Install(to = "calendar.myDataProvider", subject = "loadDelegate")
    private List<CalendarEvent> calendarMyDataProviderLoadDelegate(final ItemsFetchContext context) {
        return eventService.fetchCalendarEvents(context.getStartDate(), context.getEndDate());
    }
    // end::sample-entity-calendar-data-retriever4[]

    // tag::sample-entity-calendar-data-retriever5[]
    @ViewComponent
    private FullCalendar calendar;

    @Autowired
    private TaskEventService taskEventService;

    @Subscribe
    public void onInit(final InitEvent event) {
        calendar.addDataProvider(new CalendarDataRetriever() {
            @Override
            public List<CalendarEvent> onItemsFetch(ItemsFetchContext context) {
                return taskEventService.fetchCalendarEvents(context);
            }
        });
    }
    // end::sample-entity-calendar-data-retriever5[]
}