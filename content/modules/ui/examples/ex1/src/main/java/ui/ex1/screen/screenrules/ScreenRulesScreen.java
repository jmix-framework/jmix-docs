package ui.ex1.screen.screenrules;

import io.jmix.ui.component.Component;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.VBoxLayout;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_ScreenRulesScreen")
@UiDescriptor("screen-rules-screen.xml")
public class ScreenRulesScreen extends Screen {
    @Autowired
    private Label label;
    @Autowired
    private VBoxLayout vbox;

    @Subscribe
    public void onInit(InitEvent event) {
        // tag::label-auto[]
        label.setWidth(Component.AUTO_SIZE);
        // end::label-auto[]
        // tag::size-fix[]
        vbox.setWidth("320px");
        vbox.setHeight("240px");
        // end::size-fix[]
        // tag::size-relative[]
        label.setWidth("50%");
        // end::size-relative[]
    }
}