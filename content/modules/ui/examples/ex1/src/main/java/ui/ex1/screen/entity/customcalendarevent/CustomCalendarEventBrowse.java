package ui.ex1.screen.entity.customcalendarevent;

import io.jmix.ui.screen.*;
import ui.ex1.entity.CustomCalendarEvent;

@UiController("uiex1_CustomCalendarEvent.browse")
@UiDescriptor("custom-calendar-event-browse.xml")
@LookupComponent("customCalendarEventsTable")
public class CustomCalendarEventBrowse extends StandardLookup<CustomCalendarEvent> {
}