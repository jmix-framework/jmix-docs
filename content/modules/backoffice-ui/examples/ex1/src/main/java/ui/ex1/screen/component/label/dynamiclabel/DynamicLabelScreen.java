package ui.ex1.screen.component.label.dynamiclabel;

import io.jmix.ui.component.Label;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_DynamicLabelScreen")
@UiDescriptor("dynamicLabel-screen.xml")
public class DynamicLabelScreen extends Screen {
    // tag::dynamic-label[]
    @Autowired
    private Label dynamicLabel;

    @Subscribe
    protected void onInit(InitEvent event) {
        dynamicLabel.setValue("Some value");
    }
    // end::dynamic-label[]
}