package charts.ex1.screen.charts.data;

import io.jmix.charts.component.PieChart;
import io.jmix.ui.data.impl.MapDataItem;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_SimpleDataBindingScreen")
@UiDescriptor("simple-data-binding-screen.xml")
public class SimpleDataBindingScreen extends Screen {
    // tag::simple-data-binding[]
    @Autowired
    private PieChart pieChart;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        pieChart.addData(MapDataItem.of("key", "piece of apple pie",
                "value", 70),
                MapDataItem.of("key", "piece of blueberry pie",
                        "value", 20),
                MapDataItem.of("key", "piece of cherry pie",
                        "value", 10));
    }
    // end::simple-data-binding[]
}