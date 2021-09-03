package charts.ex1.screen.charts.export;

import charts.ex1.entity.ValueDescription;
import io.jmix.charts.component.PieChart;
import io.jmix.core.Entity;
import io.jmix.core.Metadata;
import io.jmix.ui.data.impl.EntityDataItem;
import io.jmix.ui.data.impl.ListDataProvider;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_EntityData")
@UiDescriptor("entity-data.xml")
public class EntityData extends Screen {
    // tag::entity-data-item[]
    @Autowired
    private PieChart chart;
    @Autowired
    private Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        ListDataProvider dataProvider = new ListDataProvider();
        dataProvider.addItem(new EntityDataItem(
                (Entity) valueDescription(75, "Sky")));
        dataProvider.addItem(new EntityDataItem(
                (Entity) valueDescription(7, "Shady side of pyramid")));
        dataProvider.addItem(new EntityDataItem(
                (Entity) valueDescription(18, "Sunny side of pyramid")));

        chart.setDataProvider(dataProvider);
    }

    private ValueDescription valueDescription(Integer value, String description) {
        ValueDescription entity = metadata.create(ValueDescription.class);
        entity.setValue(value);
        entity.setDescription(description);
        return entity;
    }
    // end::entity-data-item[]
}