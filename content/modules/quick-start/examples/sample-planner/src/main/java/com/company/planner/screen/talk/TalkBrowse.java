package com.company.planner.screen.talk;

import com.company.planner.service.TalksScheduleService;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Calendar;
import io.jmix.ui.screen.*;
import com.company.planner.entity.Talk;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@UiController("plnnr_Talk.browse")
@UiDescriptor("talk-browse.xml")
@LookupComponent("talksTable")
public class TalkBrowse extends StandardLookup<Talk> {

    @Autowired
    private ScreenBuilders screenBuilders;

    @Autowired
    private TalksScheduleService talksScheduleService;
    //tag::calendar-event-click[]
    @Subscribe("talksCalendar")
    public void onTalksCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<LocalDateTime> event) {
        screenBuilders.editor(Talk.class, this)
                .editEntity((Talk)event.getEntity())
                .withOpenMode(OpenMode.DIALOG)
                .withScreenClass(TalkEdit.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        getScreenData().loadAll();
                    }
                }).show();
    }
    //end::calendar-event-click[]

    //tag::calendar-event-move[]
    @Subscribe("talksCalendar")
    public void onTalksCalendarCalendarEventMove(Calendar.CalendarEventMoveEvent<LocalDateTime> event) {
        talksScheduleService.rescheduleTalk((Talk)event.getEntity(), event.getNewStart());
        getScreenData().loadAll();
    }
    //end::calendar-event-move[]

}