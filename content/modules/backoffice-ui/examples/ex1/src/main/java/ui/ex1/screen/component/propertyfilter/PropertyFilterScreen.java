package ui.ex1.screen.component.propertyfilter;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.PropertyFilter;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("propertyFilterScreen")
@UiDescriptor("property-filter-screen.xml")
public class PropertyFilterScreen extends Screen {
    @Autowired
    private Notifications notifications;

    // tag::operation-change-event[]
    @Subscribe("hobbyFilter")
    public void onHobbyFilterOperationChange(PropertyFilter.OperationChangeEvent event) {
        notifications.create()
                .withCaption("Before: " + event.getPreviousOperation() +
                        ". After: " + event.getNewOperation())
                .show();
    }
    // end::operation-change-event[]

    // tag::value-change-event[]
    @Subscribe("hobbyFilter")
    public void onHobbyFilterValueChange(HasValue.ValueChangeEvent event) {
        notifications.create()
                .withCaption("Before: " + event.getPrevValue() +
                        ". After: " + event.getValue())
                .show();
    }
    // end::value-change-event[]
}