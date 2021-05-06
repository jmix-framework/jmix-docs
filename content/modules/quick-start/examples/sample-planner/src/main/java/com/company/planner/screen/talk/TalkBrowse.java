package com.company.planner.screen.talk;

import com.company.planner.service.TalkScheduleService;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Calendar;
import io.jmix.ui.screen.*;
import com.company.planner.entity.Talk;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@UiController("planner_Talk.browse")
@UiDescriptor("talk-browse.xml")
@LookupComponent("talksTable")
public class TalkBrowse extends StandardLookup<Talk> {

    @Autowired
    private ScreenBuilders screenBuilders;

    @Autowired
    private TalkScheduleService talkScheduleService;

    //tag::calendar-event-click[]
    @Subscribe("talksCalendar")
    public void onTalksCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<LocalDateTime> event) {
        screenBuilders.editor(Talk.class, this) // <1>
                .editEntity((Talk) event.getEntity()) // <2>
                .withOpenMode(OpenMode.DIALOG) // <3>
                .withScreenClass(TalkEdit.class) // <4>
                .withAfterCloseListener(afterCloseEvent -> { // <5>
                    if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        getScreenData().loadAll();
                    }
                }).show(); // <6>
    }
    //end::calendar-event-click[]

    //tag::calendar-event-move[]
    @Subscribe("talksCalendar")
    public void onTalksCalendarCalendarEventMove(Calendar.CalendarEventMoveEvent<LocalDateTime> event) {
        talkScheduleService.rescheduleTalk((Talk) event.getEntity(), event.getNewStart()); // <1>
        getScreenData().loadAll(); // <2>
    }
    //end::calendar-event-move[]

}