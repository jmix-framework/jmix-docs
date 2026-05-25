package search.ex1.screen.event;

import io.jmix.ui.screen.*;
import search.ex1.entity.Event;

@UiController("sample_Event.edit")
@UiDescriptor("event-edit.xml")
@EditedEntityContainer("eventDc")
public class EventEdit extends StandardEditor<Event> {
}