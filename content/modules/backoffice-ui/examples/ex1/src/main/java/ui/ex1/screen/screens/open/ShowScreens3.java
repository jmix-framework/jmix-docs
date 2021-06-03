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

@UiController("sample_ShowScreens3")
@UiDescriptor("show-screens3.xml")
public class ShowScreens3 extends Screen {

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

    // tag::fancy-screen[]
    private void showFancyScreen(String message) {
        screenBuilders.screen(this)
                .withScreenClass(FancyMessageScreen.class)
                .withOptions(new FancyMessageOptions(message))
                .build()
                .show();
    }
    // end::fancy-screen[]

    // tag::show-other-screen[]
    private void openOtherScreen() {
        screenBuilders.screen(this)
                .withScreenClass(OtherScreen.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    notifications.create().withCaption("Closed " + afterCloseEvent.getSource()).show();
                })
                .build()
                .show();
    }
    // end::show-other-screen[]

    @Subscribe("showFancyScreenBtn")
    public void onShowFancyScreenBtnClick(Button.ClickEvent event) {
        showFancyScreen("Hello!!");
    }

    @Subscribe("showOtherScreenBtn")
    public void onShowOtherScreenBtnClick(Button.ClickEvent event) {
        openOtherScreen();
    }
}