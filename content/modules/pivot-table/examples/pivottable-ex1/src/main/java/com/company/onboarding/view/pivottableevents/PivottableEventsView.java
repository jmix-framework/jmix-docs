package com.company.onboarding.view.pivottableevents;


import com.company.onboarding.entity.TemperatureData;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.Messages;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.pivottableflowui.component.PivotTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "pivottable-events-view", layout = MainView.class)
@ViewController(id = "PivottableEventsView")
@ViewDescriptor(path = "pivottable-events-view.xml")
public class PivottableEventsView extends StandardView {

    @ViewComponent
    protected PivotTable<TemperatureData> pivotTable;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;

    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::cellclick[]
        pivotTable.addCellClickListener(e -> {
            List<TemperatureData> items = e.getDetail().getItems();
            StringBuilder notificationMessage = new StringBuilder();
            items.forEach(temperatureData -> {
                notificationMessage.append("id: ")
                        .append(temperatureData.getId())
                        .append(", Celsius: ")
                        .append(temperatureData.getTemperature())
                        .append("\n");
            });
            notificationMessage.deleteCharAt(notificationMessage.length() - 1);
            notifications.show("Cell click event (items in the cell)", notificationMessage.toString());
        });
        // end::cellclick[]

        // tag::refresh[]
        pivotTable.addRefreshEventListener(e -> {
            notifications.show("Refresh event",
                    "Renderer: " + messages.getMessage("pivottable.renderer." + e.getDetail().getRenderer().getId()) +
                            "\nAggregation: " + messages.getMessage("pivottable.aggregator." + e.getDetail().getAggregationMode().getId()) +
                            "\nRows: " + String.join("", e.getDetail().getRows()) +
                            "\nColumns: " + String.join("", e.getDetail().getColumns()));

        });
        // end::refresh[]
    }
}