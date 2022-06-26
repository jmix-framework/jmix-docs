package charts.ex1.screen.charts.examples;

import charts.ex1.entity.CountryGrowth;
import io.jmix.charts.component.Chart;
import io.jmix.charts.component.SerialChart;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.ContentMode;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

// tag::column3d-chart1[]
@UiController("sample_Column3dChart")
@UiDescriptor("column3d-chart.xml")
public class Column3dChart extends Screen {

    @Autowired
    private CollectionContainer<CountryGrowth> countryGrowthDc;

    // end::column3d-chart1[]

    // tag::notifications[]
    @Autowired
    private SerialChart column3d; // <1>

    @Autowired
    private Notifications notifications; // <2>

    // end::notifications[]

    // tag::column3d-chart2[]
    @Subscribe
    private void onInit(InitEvent event) {
        // end::column3d-chart2[]
        // tag::listener[]
        column3d.addGraphItemClickListener(graphItemClickEvent -> // <3>
                notifications.create()
                        .withCaption(itemClickEventInfo(graphItemClickEvent))
                        .withContentMode(ContentMode.HTML)
                        .show());
        // ...
        // end::listener[]
        // tag::column3d-chart3[]
        List<CountryGrowth> items = new ArrayList<>();
        items.add(countryGrowth("USA", 3.5, 4.2));
        items.add(countryGrowth("UK", 1.7, 3.1));
        items.add(countryGrowth("Canada", 2.8, 2.9));
        items.add(countryGrowth("Japan", 2.6, 2.3));
        items.add(countryGrowth("France", 1.4, 2.1));
        items.add(countryGrowth("Brazil", 2.6, 4.9));
        items.add(countryGrowth("Russia", 6.4, 7.2));
        items.add(countryGrowth("India", 8.0, 7.1));
        items.add(countryGrowth("China", 9.9, 10.1));
        countryGrowthDc.setItems(items);
        // end::column3d-chart3[]
        // tag::column3d-chart4[]
    }
    // end::column3d-chart4[]

    // tag::country-growth[]
    private CountryGrowth countryGrowth(String country, double year2020, double year2021) {
        CountryGrowth cg = new CountryGrowth();
        cg.setCountry(country);
        cg.setYear2020(year2020);
        cg.setYear2021(year2021);
        return cg;
    }
    // end::country-growth[]

    // tag::event-info[]
    private String itemClickEventInfo(Chart.GraphItemClickEvent event) { // <4>
        CountryGrowth countryGrowth = (CountryGrowth) event.getEntityNN(); // <5>
        return String.format("GDP grow in %s (%s): %.1f%%",
                countryGrowth.getCountry(),
                event.getGraphId().substring(5),
                "graph2020".equals(event.getGraphId()) ? countryGrowth.getYear2020() : countryGrowth.getYear2021());
    }
    // end::event-info[]

    // tag::column3d-chart5[]
}
// end::column3d-chart5[]