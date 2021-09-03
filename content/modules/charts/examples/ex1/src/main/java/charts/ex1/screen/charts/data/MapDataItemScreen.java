package charts.ex1.screen.charts.data;

import com.google.common.collect.ImmutableMap;
import io.jmix.charts.component.PieChart;
import io.jmix.ui.data.impl.ListDataProvider;
import io.jmix.ui.data.impl.MapDataItem;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_MapDataItemScreen")
@UiDescriptor("map-data-item-screen.xml")
public class MapDataItemScreen extends Screen {
    // tag::map-data-item[]
    @Autowired
    private PieChart chart;

    @Subscribe
    protected void onInit(InitEvent event) {
        ListDataProvider dataProvider = new ListDataProvider();
        dataProvider.addItem(new MapDataItem(
                ImmutableMap.of("value", 75, "description", "Sky")));
        dataProvider.addItem(new MapDataItem(
                ImmutableMap.of("value", 7, "description", "Shady side of pyramid")));
        dataProvider.addItem(new MapDataItem(
                ImmutableMap.of("value", 18, "description", "Sunny side of pyramid")));

        chart.setDataProvider(dataProvider);
        // end::map-data-item[]
    }
}