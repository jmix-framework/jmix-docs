package ui.ex1.screen.component.checkbox;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.CheckBox;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("CheckBoxScreen")
@UiDescriptor("check-box-screen.xml")
public class CheckBoxScreen extends Screen {
    // tag::check-box[]
    @Autowired
    private CheckBox checkBox;
    @Autowired
    private Notifications notifications;

    @Subscribe
    protected void onInit(InitEvent event) {
        checkBox.addValueChangeListener(valueChangeEvent -> {
            if (Boolean.TRUE.equals(valueChangeEvent.getValue())) {
                notifications.create()
                        .withCaption("set")
                        .show();
            } else {
                notifications.create()
                        .withCaption("not set")
                        .show();
            }
        });
    }
    // end::check-box[]
}