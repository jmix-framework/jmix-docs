package com.company.planner.service;

import com.company.planner.entity.Talk;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
//tag::task-schedule-service[]
@Service
public class TalksScheduleService {

    @Autowired
    private DataManager dataManager;

    public Talk rescheduleTalk(Talk talk, LocalDateTime newStartDate){
        LocalDateTime dayStart = newStartDate.truncatedTo(ChronoUnit.DAYS).withHour(8);
        LocalDateTime dayEnd = newStartDate.truncatedTo(ChronoUnit.DAYS).withHour(19);

        Long talksSameTime = dataManager.loadValue("select count(t) from plnnr_Talk t where " +
                "(t.startDate between :dayStart and :dayEnd) " +
                "and t.speaker = :speaker " +
                "and t.id <> :talkId", Long.class)
                .parameter("dayStart", dayStart)
                .parameter("dayEnd", dayEnd)
                .parameter("speaker", talk.getSpeaker())
                .parameter("talkId", talk.getId())
                .one();
        if (talksSameTime >= 2) {
            return talk;
        }
        talk.setStartDate(newStartDate);
        return dataManager.save(talk);
    }

}
//end::task-schedule-service[]