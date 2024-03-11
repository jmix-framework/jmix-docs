package com.company.demo.view.gauge;

import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.kit.component.model.series.GaugeSeries;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.view.*;

import java.util.Random;

@ViewController("gauge-series-colorized")
@ViewDescriptor("gauge-series-colorized.xml")
public class GaugeSeriesColorizedSample extends StandardView {

    @ViewComponent
    protected Chart chart;

    protected Random random = new Random();

    @Subscribe("timer")
    protected void onTimerTick(Timer.TimerActionEvent event) {
        // tag::dataBinding[]
        GaugeSeries gaugeSeries = chart.getSeries("gaugeSeries");

        gaugeSeries.setData(
                new GaugeSeries.DataItem()
                        .withValue((double) random.nextInt(0, 100))
        );
        // end::dataBinding[]
    }
}
