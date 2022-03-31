package ui.ex1.screen.facets.clipboardtrigger;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ClipboardTrigger;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_ClipboardTriggerScreen")
@UiDescriptor("clipboard-trigger-screen.xml")
public class ClipboardTriggerScreen extends Screen {

    // tag::clipboard-trigger-usage-example[]
    @Autowired
    private Notifications notifications;
    @Autowired
    private ClipboardTrigger clipboardTrigger;

    @Subscribe("btn")
    public void onBtnClick(Button.ClickEvent event) {
        if (clipboardTrigger.isSupportedByWebBrowser()) {
            notifications.create()
                    .withCaption("Phone number was copied to clipboard")
                    .show();
        } else {
            notifications.create()
                    .withCaption("The phone number is not copied, because your " +
                            "browser does not support this functionality")
                    .show();
        }
    }
    // end::clipboard-trigger-usage-example[]

    // tag::clipboard-trigger-copy-event[]
    @Subscribe("clipboardTrigger")
    public void onClipboardTriggerCopy(ClipboardTrigger.CopyEvent event) {
        if (event.isSuccess()) {
            notifications.create()
                    .withCaption("Text was successfully copied to clipboard")
                    .show();
        } else {
            notifications.create()
                    .withCaption("Something went wrong during copying")
                    .show();
        }
    }
    // end::clipboard-trigger-copy-event[]
}