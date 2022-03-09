package ui.ex1.screen.component.calendar;

import io.jmix.ui.component.Calendar;
import io.jmix.ui.component.calendar.SimpleCalendarEvent;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@UiController("sample_CalendarScreen")
@UiDescriptor("calendar-screen.xml")
public class CalendarScreen extends Screen {
    // tag::calendar[]
    @Autowired
    private Calendar<Date> calendar;

    // end::calendar[]

    // tag::customized-calendar[]
    @Autowired
    private Calendar<Date> customizedCalendar;

    // end::customized-calendar[]

    // tag::init-start[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::init-start[]

        // tag::direct-adding[]
        SimpleCalendarEvent<Date> simpleCalendarEvent = new SimpleCalendarEvent<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        simpleCalendarEvent.setCaption("Development");
        simpleCalendarEvent.setDescription("Platform development");
        simpleCalendarEvent.setStart(simpleDateFormat.parse("2021-05-26 15:00", new ParsePosition(0)));
        simpleCalendarEvent.setEnd(simpleDateFormat.parse("2021-05-26 19:00", new ParsePosition(0)));
        simpleCalendarEvent.setAllDay(false);

        calendar.getEventProvider().addEvent(simpleCalendarEvent);
        // end::direct-adding[]

        // tag::style-red[]
        simpleCalendarEvent.setStyleName("event-red");
        // end::style-red[]


        // tag::customized-calendar-1[]
        Map<DayOfWeek, String> days = new HashMap<>(7);
        days.put(DayOfWeek.MONDAY,"Moon");
        days.put(DayOfWeek.TUESDAY,"Mars");
        days.put(DayOfWeek.WEDNESDAY,"Mercury");
        days.put(DayOfWeek.THURSDAY,"Jupiter");
        days.put(DayOfWeek.FRIDAY,"Venus");
        days.put(DayOfWeek.SATURDAY,"Saturn");
        days.put(DayOfWeek.SUNDAY,"Sun");
        customizedCalendar.setDayNames(days);
        // end::customized-calendar-1[]

        // tag::init-end[]
    }
    // end::init-end[]
}