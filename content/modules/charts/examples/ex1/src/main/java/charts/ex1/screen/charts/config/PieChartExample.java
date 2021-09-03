package charts.ex1.screen.charts.config;

import com.google.common.collect.ImmutableMap;
import io.jmix.charts.component.PieChart;
import io.jmix.charts.model.Align;
import io.jmix.charts.model.Color;
import io.jmix.charts.model.MarkerType;
import io.jmix.charts.model.label.Label;
import io.jmix.charts.model.legend.Legend;
import io.jmix.charts.model.legend.LegendPosition;
import io.jmix.ui.data.DataProvider;
import io.jmix.ui.data.impl.ListDataProvider;
import io.jmix.ui.data.impl.MapDataItem;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@UiController("sample_PieChartExample")
@UiDescriptor("pie-chart-example.xml")
public class PieChartExample extends Screen {

    //tag::pie-chart-example[]
    @Autowired
    private PieChart pieChart;

    @Subscribe
    public void onInit(InitEvent event) {
        pieChart.setWidth("700px");
        pieChart.setTitleField("description")
                .setValueField("value")
                .setStartAngle(312)
                .setLegend(new Legend()
                        .setMarkerType(MarkerType.CIRCLE)
                        .setPosition(LegendPosition.RIGHT)
                        .setMarginRight(80))
                .addLabels(
                        new Label()
                                .setText("Sample Chart")
                                .setSize(26)
                                .setBold(true)
                                .setAlign(Align.CENTER),
                        new Label()
                                .setText("extra information")
                                .setAlign(Align.RIGHT))
                .setLabelTickColor(Color.GOLDENROD)
                .setColors(Arrays.asList(
                        Color.valueOf("#446493"),
                        Color.valueOf("#5E3D2C"),
                        Color.valueOf("#D0A557")))
                .setDataProvider(initDataProvider());
    }

    private DataProvider initDataProvider() {
        ListDataProvider dataProvider = new ListDataProvider();
        dataProvider.addItem(new MapDataItem(
                ImmutableMap.of("value", 75, "description", "Sky")));
        dataProvider.addItem(new MapDataItem(
                ImmutableMap.of("value", 7, "description", "Shady side of pyramid")));
        dataProvider.addItem(new MapDataItem(
                ImmutableMap.of("value", 18, "description", "Sunny side of pyramid")));
        return dataProvider;
    }
    //end::pie-chart-example[]
}