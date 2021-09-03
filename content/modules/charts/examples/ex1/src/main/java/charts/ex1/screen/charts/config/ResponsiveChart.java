package charts.ex1.screen.charts.config;

import io.jmix.charts.component.SerialChart;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@UiController("sample_ResponsiveChart")
@UiDescriptor("responsive-chart.xml")
public class ResponsiveChart extends Screen {

    @Autowired
    private ComboBox<String> resolutionsField;
    @Autowired
    private SerialChart stackedArea;

    @Subscribe
    protected void onInit(InitEvent event) {
        resolutionsField.setTextInputAllowed(false);
        resolutionsField.setNullOptionVisible(false);
        resolutionsField.setOptionsList(Arrays.asList(
                "800x500", "800x300", "800x200", "800x100",
                "350x500", "200x500", "100x500",
                "100x30", "30x100"
        ));

        resolutionsField.setValue("800x500");
    }

    @Subscribe("resolutionsField")
    protected void onResolutionsFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        if (event.getValue() != null) {
            String[] size = event.getValue().split("x");
            stackedArea.setWidth(size[0] + "px");
            stackedArea.setHeight(size[1] + "px");
        }
    }
}