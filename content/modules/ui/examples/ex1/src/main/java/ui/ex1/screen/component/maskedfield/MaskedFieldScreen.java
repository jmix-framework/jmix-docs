package ui.ex1.screen.component.maskedfield;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.MaskedField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_MaskedFieldScreen")
@UiDescriptor("masked-field-screen.xml")
public class MaskedFieldScreen extends Screen {
    @Autowired
    protected MaskedField<String> phoneFieldMasked;
    @Autowired
    protected MaskedField<String> phoneFieldClear;

    @Autowired
    protected Notifications notifications;

    @Subscribe("showMasked")
    protected void onShowMaskedClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption(phoneFieldMasked.getValue())
                .show();
    }

    @Subscribe("showClear")
    protected void onShowClearClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption(phoneFieldClear.getValue())
                .show();
    }
}