package com.company.calendarsex1.calendars;

import io.jmix.businesscalendar.annotation.*;

import java.time.DayOfWeek;
import java.time.Month;

// tag::business-calendar[]
@BusinessCalendar(name = "Sample Business Calendar",
        code = "sample-business-calendar") // <1>
public interface SampleBusinessCalendar {

    @CronHoliday(expression = "* * * 1-2 MAY ?") // <2>
    @CronHoliday(expression = "* * * ? * 6#3")
    void cronHoliday();

    @WeeklyHoliday(DayOfWeek.SATURDAY)
    @WeeklyHoliday(value = DayOfWeek.SUNDAY,
            description = "Sunday is a day for family") // <3>
    void weeklyHolidays();

    @FixedDayHoliday(fixedDate = "2024-05-08") // <4>
    @FixedDayHoliday(fixedDate = "2024-05-09")
    void fixedHoliday();

    @FixedYearlyHoliday(month = Month.NOVEMBER, dayOfMonth = 4) // <5>
    @FixedYearlyHoliday(month = Month.JUNE, dayOfMonth = 12)
    void fixedYearlyHoliday();

    @ScheduledBusinessDay(dayOfWeek = DayOfWeek.MONDAY,
            startTime = "08:00", endTime = "17:00") // <6>
    @ScheduledBusinessDay(dayOfWeek = DayOfWeek.WEDNESDAY,
            startTime = "09:00", endTime = "17:00")
    @ScheduledBusinessDay(dayOfWeek = DayOfWeek.FRIDAY,
            startTime = "10:00", endTime = "15:00")
    void scheduledBD();

    @AdditionalBusinessDay(fixedDate = "2024-05-06",
            startTime = "10:00", endTime = "16:30") // <7>
    @AdditionalBusinessDay(fixedDate = "2024-05-07",
            startTime = "10:00", endTime = "16:30")
    void additionalBD();
}
// end::business-calendar[]
