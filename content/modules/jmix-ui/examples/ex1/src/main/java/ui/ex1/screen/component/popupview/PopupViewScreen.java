package ui.ex1.screen.component.popupview;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.PopupView;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_PopupViewScreen")
@UiDescriptor("popup-view-screen.xml")
public class PopupViewScreen extends Screen {
    // tag::position[]
    @Autowired
    private PopupView popupView;

    @Subscribe
    public void onInit(InitEvent event) {
        popupView.setPopupPosition(PopupView.PopupPosition.TOP_CENTER);
    }
    // end::position[]

    // tag::popup-visibility-event[]
    @Autowired
    private Notifications notifications;

    @Subscribe("popupView")
    public void onPopupViewPopupVisibility(PopupView.PopupVisibilityEvent event) {
        notifications.create()
                .withCaption(event.isPopupVisible() ?
                        "The popup is visible" : "The popup is hidden")
                .withType(Notifications.NotificationType.HUMANIZED)
                .show();
    }
    // end::popup-visibility-event[]
}