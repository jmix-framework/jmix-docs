package com.company.demo.view.responsivechart;


import com.company.demo.entity.NamedIntegerEntity;
import com.company.demo.entity.VehicleCount;
import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.kit.component.model.Title;
import io.jmix.chartsflowui.kit.component.model.legend.Legend;
import io.jmix.core.DataManager;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "responsive-chart-view", layout = MainView.class)
@ViewController(id = "ResponsiveChartView")
@ViewDescriptor(path = "responsive-chart-view.xml")
public class ResponsiveChartView extends StandardView {
    @ViewComponent
    private Chart barChart;
    @ViewComponent
    private CollectionContainer<NamedIntegerEntity> vehiclesIn2012Dc;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onInit(final InitEvent event) {
        barChart.setLegend(new Legend().withTop("0"));
        barChart.setTitle(new Title().withText("Vehicles").withSubtext("By Year"));
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
    }
}