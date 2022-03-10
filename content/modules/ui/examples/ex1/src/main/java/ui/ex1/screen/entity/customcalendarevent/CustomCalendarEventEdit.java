package ui.ex1.screen.entity.customcalendarevent;

import io.jmix.ui.screen.*;
import ui.ex1.entity.CustomCalendarEvent;

@UiController("uiex1_CustomCalendarEvent.edit")
@UiDescriptor("custom-calendar-event-edit.xml")
@EditedEntityContainer("customCalendarEventDc")
public class CustomCalendarEventEdit extends StandardEditor<CustomCalendarEvent> {
}