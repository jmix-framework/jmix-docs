package ui.ex1.screen.component.popupview;

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
}