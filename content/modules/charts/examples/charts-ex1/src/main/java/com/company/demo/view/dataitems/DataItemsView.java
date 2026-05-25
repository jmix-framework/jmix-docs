package com.company.demo.view.dataitems;

import com.company.demo.entity.ValueDescription;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.EntityDataItem;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.data.item.SimpleDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.core.Metadata;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Route(value = "data-items", layout = MainView.class)
@ViewController("DataItemsView")
@ViewDescriptor("data-items-view.xml")
public class DataItemsView extends StandardView {

    @Autowired
    protected Metadata metadata;

    @ViewComponent
    protected Chart entityChart;

    @ViewComponent
    protected Chart mapChart;

    @ViewComponent
    protected Chart simpleChart;

    @Subscribe
    protected void onInit(InitEvent event) {

        // tag::simpleDataItems[]
        ListChartItems<SimpleDataItem> simpleChartItems = new ListChartItems<>(
                List.of(
                        new SimpleDataItem(new ValueDescriptionDTO(75, "Sky")),
                        new SimpleDataItem(new ValueDescriptionDTO(7, "Shady side of pyramid")),
                        new SimpleDataItem(new ValueDescriptionDTO(18, "Sunny side of pyramid"))
                )
        );

        simpleChart.withDataSet(
                new DataSet().withSource(
                        new DataSet.Source<SimpleDataItem>()
                                .withDataProvider(simpleChartItems)
                                .withCategoryField("description")
                                .withValueField("value")
                )
        );
        // end::simpleDataItems[]

        // tag::mapDataItems[]
        ListChartItems<MapDataItem> mapChartItems = new ListChartItems<>(
                List.of(
                        new MapDataItem(Map.of("value", 75, "description", "Sky")),
                        new MapDataItem(Map.of("value", 7, "description", "Shady side of pyramid")),
                        new MapDataItem(Map.of("value", 18, "description", "Sunny side of pyramid"))
                )
        );

        mapChart.withDataSet(
                new DataSet().withSource(
                        new DataSet.Source<MapDataItem>()
                                .withDataProvider(mapChartItems)
                                .withCategoryField("description")
                                .withValueField("value")
                )
        );
        // end::mapDataItems[]

        // tag::entityDataItems[]

        // ...

        ListChartItems<EntityDataItem> entityChartItems = new ListChartItems<>(
                List.of(
                        new EntityDataItem(createValueDescriptionEntity(75, "Sky")),
                        new EntityDataItem(createValueDescriptionEntity(7, "Shady side of pyramid")),
                        new EntityDataItem(createValueDescriptionEntity(18, "Sunny side of pyramid"))
                )
        );

        entityChart.withDataSet(
                new DataSet().withSource(
                        new DataSet.Source<EntityDataItem>()
                                .withDataProvider(entityChartItems)
                                .withCategoryField("description")
                                .withValueField("value")
                )
        );
    }


    protected ValueDescription createValueDescriptionEntity(Integer value, String description) {
        ValueDescription entity = metadata.create(ValueDescription.class);
        entity.setValue(value);
        entity.setDescription(description);
        return entity;
    }
    // end::entityDataItems[]

    public static class ValueDescriptionDTO {
        protected Integer value;
        protected String description;

        public ValueDescriptionDTO(Integer value, String description) {
            this.value = value;
            this.description = description;
        }

        public Integer getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }
    }
}
