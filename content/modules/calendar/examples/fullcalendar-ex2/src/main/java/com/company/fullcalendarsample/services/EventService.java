package com.company.fullcalendarsample.services;

import io.jmix.fullcalendarflowui.component.data.CalendarEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EventService {

    public List<CalendarEvent> fetchCalendarEvents(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }
}
