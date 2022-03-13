package ui.ex1.screen.urlhistorynav;

import com.google.common.collect.ImmutableMap;
import io.jmix.core.DataManager;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlIdSerializer;
import io.jmix.ui.navigation.UrlParamsChangedEvent;
import io.jmix.ui.navigation.UrlRouting;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Event;

import java.util.UUID;

// tag::event-info[]
@UiController("eventInfo")
@UiDescriptor("event-info.xml")
@Route("event-info")
// end::event-info[]
// tag::event-info-start[]
public class EventInfo extends Screen {
    // end::event-info-start[]
    // tag::inject-event-field[]
    @Autowired
    protected EntityComboBox<Event> eventField;

    // end::inject-event-field[]
    // tag::inject-url-routing[]
    @Autowired
    protected UrlRouting urlRouting;

    // end::inject-url-routing[]
    @Autowired
    protected DataManager dataManager;

    // tag::select-btn[]
    @Subscribe("selectBtn")
    protected void onSelectBtnClick(Button.ClickEvent e) {
        Event event = eventField.getValue(); // <1>
        if (event == null){
            urlRouting.replaceState(this); // <2>
            return;
        }
        String serializedEventId = UrlIdSerializer.serializeId(event.getId()); // <3>

        urlRouting.replaceState(this, ImmutableMap.of("event_id", serializedEventId)); // <4>
    }
    // end::select-btn[]
    // tag::url-params-changed-event[]
    @Subscribe
    protected void onUrlParamsChanged(UrlParamsChangedEvent event) {
        String serializedEventId = event.getParams().get("event_id"); // <1>

        UUID eventId = (UUID) UrlIdSerializer.deserializeId(UUID.class, serializedEventId); // <2>

        eventField.setValue(dataManager.load(Event.class).id(eventId).one()); // <3>
    }
    // end::url-params-changed-event[]
    // tag::event-info-end[]
}
// end::event-info-end[]
