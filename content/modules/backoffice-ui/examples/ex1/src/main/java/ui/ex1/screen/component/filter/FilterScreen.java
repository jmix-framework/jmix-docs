package ui.ex1.screen.component.filter;

import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Collapsable;
import io.jmix.ui.component.Filter;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("filterScreen")
@UiDescriptor("filter-screen.xml")
public class FilterScreen extends Screen {
    @Autowired
    private Notifications notifications;
    // tag::configuration-change-event[]
    @Subscribe("filter")
    public void onFilterConfigurationChange(Filter.ConfigurationChangeEvent event) {
        notifications.create()
                .withCaption("Before: " + event.getPreviousConfiguration().getName() +
                        ". After: " + event.getNewConfiguration().getName())
                .show();
    }
    // end::configuration-change-event[]
    // tag::expanded-state-change-event[]
    @Subscribe("filter")
    public void onFilterExpandedStateChange(Collapsable.ExpandedStateChangeEvent event) {
        notifications.create()
                .withCaption("Expanded: " + event.isExpanded())
                .show();
    }
    // end::expanded-state-change-event[]

    // tag::properties-filter-predicate[]
    @Install(to = "filter", subject = "propertiesFilterPredicate")
    private boolean filterPropertiesFilterPredicate(MetaPropertyPath metaPropertyPath) {
        return !metaPropertyPath.getMetaProperty().getName().equals("hobby");
    }
    // end::properties-filter-predicate[]
}