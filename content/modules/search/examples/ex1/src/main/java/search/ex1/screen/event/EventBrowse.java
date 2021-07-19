package search.ex1.screen.event;

import io.jmix.ui.screen.*;
import search.ex1.entity.Event;

@UiController("sample_Event.browse")
@UiDescriptor("event-browse.xml")
@LookupComponent("eventsTable")
public class EventBrowse extends StandardLookup<Event> {
}