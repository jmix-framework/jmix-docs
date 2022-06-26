package charts.ex1.screen.charts.examples;

import io.jmix.charts.component.SerialChart;
import io.jmix.ui.data.DataItem;
import io.jmix.ui.data.impl.ListDataProvider;
import io.jmix.ui.data.impl.MapDataItem;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

// tag::stackedarea-chart[]
@UiController("sample_StackedareaChart")
@UiDescriptor("stackedarea-chart.xml")
public class StackedareaChart extends Screen {
    @Autowired
    private SerialChart chart;

    @Subscribe
    private void onInit(InitEvent event) {
        ListDataProvider dataProvider = new ListDataProvider();
        dataProvider.addItem(transportCount(2003, 1587, 650, 121));
        dataProvider.addItem(transportCount(2004, 1567, 683, 146));
        dataProvider.addItem(transportCount(2005, 1617, 691, 138));
        dataProvider.addItem(transportCount(2006, 1630, 642, 127));
        dataProvider.addItem(transportCount(2007, 1660, 699, 105));
        dataProvider.addItem(transportCount(2008, 1683, 721, 109));
        dataProvider.addItem(transportCount(2009, 1691, 737, 112));
        dataProvider.addItem(transportCount(2010, 1298, 680, 101));
        dataProvider.addItem(transportCount(2011, 1275, 664, 97));
        dataProvider.addItem(transportCount(2012, 1246, 648, 93));
        dataProvider.addItem(transportCount(2013, 1318, 697, 111));
        dataProvider.addItem(transportCount(2014, 1213, 633, 87));
        dataProvider.addItem(transportCount(2015, 1199, 621, 79));
        dataProvider.addItem(transportCount(2016, 1110, 210, 81));
        dataProvider.addItem(transportCount(2017, 1165, 232, 75));
        dataProvider.addItem(transportCount(2018, 1145, 219, 88));
        dataProvider.addItem(transportCount(2019, 1163, 201, 82));
        dataProvider.addItem(transportCount(2020, 1180, 285, 87));
        dataProvider.addItem(transportCount(2021, 1159, 277, 71));

        chart.setDataProvider(dataProvider);
    }

    private DataItem transportCount(int year, int cars, int motorcycles, int bicycles) {
        MapDataItem item = new MapDataItem();
        item.add("year", year);
        item.add("cars", cars);
        item.add("motorcycles", motorcycles);
        item.add("bicycles", bicycles);
        return item;
    }
}
// end::stackedarea-chart[]