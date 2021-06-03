package ui.ex1.screen.screens.open;

import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_ShowScreens2")
@UiDescriptor("show-screens2.xml")
public class ShowScreens2 extends Screen {

    // tag::screens[]
    @Autowired
    private Screens screens;

    // end::screens[]

    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    // tag::screen-builders[]
    @Autowired
    private ScreenBuilders screenBuilders;

    // end::screen-builders[]


    // tag::show-fancy-message[]
    private void showFancyScreen(String message) {
        FancyMessageScreen screen = screenBuilders.screen(this)
                .withScreenClass(FancyMessageScreen.class)
                .build();
        screen.setFancyMessage(message);
        screen.show();
    }
    // end::show-fancy-message[]

    // tag::show-other-screen[]
    private void openOtherScreen() {
        OtherScreen otherScreen = screens.create(OtherScreen.class);
        otherScreen.addAfterCloseListener(afterCloseEvent -> {
            notifications.create().withCaption("Closed " + afterCloseEvent.getSource()).show();
        });
        otherScreen.show();
    }
    // end::show-other-screen[]

    @Subscribe("showFancyScreenBtn")
    public void onShowFancyScreenBtnClick(Button.ClickEvent event) {
        showFancyScreen("Hello!");
    }

    @Subscribe("showOtherScreenBtn")
    public void onShowOtherScreenBtnClick(Button.ClickEvent event) {
        openOtherScreen();
    }
}