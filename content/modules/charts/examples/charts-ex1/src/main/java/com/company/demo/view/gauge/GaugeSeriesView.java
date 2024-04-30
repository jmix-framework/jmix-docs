package com.company.demo.view.gauge;

import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.kit.component.model.series.GaugeSeries;
import io.jmix.flowui.view.*;


@Route(value = "gauge-series", layout = MainView.class)
@ViewController("GaugeSeriesView")
@ViewDescriptor("gauge-series-view.xml")
public class GaugeSeriesView extends StandardView {

    @ViewComponent
    protected Chart chart;

    @Subscribe
    protected void onInit(InitEvent event) {
        // tag::dataBinding[]
        GaugeSeries gaugeSeries = chart.getSeries("gaugeSeries");

        gaugeSeries.setData(
                new GaugeSeries.DataItem()
                        .withValue(50.0).withName("SCORE")
        );
        // end::dataBinding[]
    }
}
