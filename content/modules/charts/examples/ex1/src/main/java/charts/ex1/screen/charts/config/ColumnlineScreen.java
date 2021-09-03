package charts.ex1.screen.charts.config;

import io.jmix.charts.component.SerialChart;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@UiController("sample_ColumnlineScreen")
@UiDescriptor("columnline-screen.xml")
public class ColumnlineScreen extends Screen {

    // tag::add-fields[]
    @Autowired
    private SerialChart serialChart;

    @Subscribe
    public void onInit(InitEvent event) {
        List<String> fields = Arrays.asList("income", "year");
        serialChart.setAdditionalFields(fields);
    }
    // end::add-fields[]
}