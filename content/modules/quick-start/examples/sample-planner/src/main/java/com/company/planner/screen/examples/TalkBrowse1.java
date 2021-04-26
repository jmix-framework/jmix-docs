package com.company.planner.screen.examples;

import io.jmix.ui.component.Calendar;
import io.jmix.ui.screen.*;
import com.company.planner.entity.Talk;

import java.time.LocalDateTime;

@UiController("planner_Talk1.browse")
@UiDescriptor("talk-browse1.xml")
@LookupComponent("talksTable")
public class TalkBrowse1 extends StandardLookup<Talk> {

    // tag::event-click[]
    @Subscribe("talksCalendar")
    public void onTalksCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<LocalDateTime> event) {

    }
    // end::event-click[]
}