package com.company.fullcalendarsample.services;

import io.jmix.fullcalendarflowui.component.data.CalendarEvent;
import io.jmix.fullcalendarflowui.component.data.CallbackCalendarDataProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskEventService {

    public List<CalendarEvent> fetchCalendarEvents(CallbackCalendarDataProvider.ItemsFetchContext context) {
        return List.of();
    }
}