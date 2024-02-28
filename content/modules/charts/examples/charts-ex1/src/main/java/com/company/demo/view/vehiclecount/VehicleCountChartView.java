package com.company.demo.view.vehiclecount;


import com.company.demo.entity.NamedIntegerEntity;
import com.company.demo.entity.VehicleCount;
import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.ContainerChartItems;
import io.jmix.chartsflowui.data.item.EntityDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Title;
import io.jmix.chartsflowui.kit.component.model.legend.Legend;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.KeyValueCollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "vehicle-count-chart", layout = MainView.class)
@ViewController("VehicleCountChartView")
@ViewDescriptor("vehicle-count-chart-view.xml")
public class VehicleCountChartView extends StandardView {

    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private CollectionContainer<VehicleCount> vehiclesDc;
    @ViewComponent
    private CollectionContainer<NamedIntegerEntity> vehiclesIn2012Dc;

    // tag::configure[]
    @ViewComponent
    private Chart chart;

    @Subscribe
    public void onInit(final InitEvent event) {
        chart.setLegend(new Legend().withTop("0"));
        chart.setTitle(new Title().withText("Vehicles").withSubtext("By Year"));
    }
    // end::configure[]


    private void connectChartToDataContainer() {
        // tag::dataSet[]
        chart.setDataSet(
                new DataSet().withSource(
                        new DataSet.Source<EntityDataItem>()
                                .withDataProvider(new ContainerChartItems<>(vehiclesDc))
                                .withCategoryField("year")
                                .withValueFields("cars", "motorcycles", "bicycles")
                )
        );
        // end::dataSet[]
    }


    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        VehicleCount vehicleCount = dataManager.load(VehicleCount.class)
                .query("e.year = 2012")
                .one();
        vehiclesIn2012Dc.setItems(List.of(
                NamedIntegerEntity.create("cars", vehicleCount.getCars()),
                NamedIntegerEntity.create("motorcycles", vehicleCount.getMotorcycles()),
                NamedIntegerEntity.create("bicycles", vehicleCount.getBicycles())

        ));
//        addVehicleCountEntityToDataContainer("cars", vehicleCount.getCars());
//        addVehicleCountEntityToDataContainer("motorcycles", vehicleCount.getMotorcycles());
//        addVehicleCountEntityToDataContainer("bicycles", vehicleCount.getBicycles());
    }

//    private void addVehicleCountEntityToDataContainer(String cars, Integer vehicleCount) {
//        KeyValueEntity entity = new KeyValueEntity();
//        entity.setValue("name", entity);
//        entity.setValue("value", vehicleCount);
//        vehiclesIn2012Dc.getMutableItems().add(entity);
//    }
}