package com.company.planner.service;

import com.company.planner.entity.Talk;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

//tag::task-schedule-service[]
@Component("planner_TaskScheduleService")
public class TalksScheduleService {

    @Autowired
    private DataManager dataManager;

    public Talk rescheduleTalk(Talk talk, LocalDateTime newStartDate) { // <1>
        LocalDateTime dayStart = newStartDate.truncatedTo(ChronoUnit.DAYS).withHour(8); // <2>
        LocalDateTime dayEnd = newStartDate.truncatedTo(ChronoUnit.DAYS).withHour(19); // <3>

        Long talksSameTime = dataManager.loadValue("select count(t) " + // <4>
                "from planner_Talk t where " +
                "(t.startDate between :dayStart and :dayEnd) " +
                "and t.speaker = :speaker " +
                "and t.id <> :talkId", Long.class)
                .parameter("dayStart", dayStart)
                .parameter("dayEnd", dayEnd)
                .parameter("speaker", talk.getSpeaker())
                .parameter("talkId", talk.getId())
                .one();
        if (talksSameTime >= 2) { // <5>
            return talk; // <6>
        }
        talk.setStartDate(newStartDate); // <7>
        return dataManager.save(talk); // <8>
    }

}
//end::task-schedule-service[]