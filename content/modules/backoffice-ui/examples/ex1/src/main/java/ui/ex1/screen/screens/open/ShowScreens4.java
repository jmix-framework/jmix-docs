package ui.ex1.screen.screens.open;

import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@UiController("sample_ShowScreens4")
@UiDescriptor("show-screens4.xml")
public class ShowScreens4 extends Screen {

    // tag::screens[]
    @Autowired
    private Screens screens;

    // end::screens[]

    // tag::screen-builders[]
    @Autowired
    private ScreenBuilders screenBuilders;

    // end::screen-builders[]

    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    private void showFancyScreen(String message) {
        Map<String, Object> options = new HashMap<>();
        options.put("message", message);
        screenBuilders.screen(this)
                .withScreenClass(FancyMessageScreen2.class)
                .withOptions(new MapScreenOptions(options))
                .build()
                .show();
    }

    // tag::show-other-screen[]
    private void openOtherScreen() {
        screenBuilders.screen(this)
                .withScreenClass(OtherScreen.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    OtherScreen otherScreen = afterCloseEvent.getSource();
                    if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        String result = otherScreen.getResult();
                        notifications.create().withCaption("Result: " + result).show();
                    }
                })
                .build()
                .show();
    }
    // end::show-other-screen[]

    // tag::show-new-other-screen[]
    private void openNewOtherScreen() {
        Screen otherScreen = screens.create("sample_NewOtherScreen", OpenMode.THIS_TAB);
        otherScreen.addAfterCloseListener(afterCloseEvent -> {
            CloseAction closeAction = afterCloseEvent.getCloseAction();
            if (closeAction instanceof MyCloseAction) {
                String result = ((MyCloseAction) closeAction).getResult();
                notifications.create().withCaption("Result: " + result).show();
            }
        });
        otherScreen.show();
    }
    // end::show-new-other-screen[]

    @Subscribe("showFancyScreenBtn")
    public void onShowFancyScreenBtnClick(Button.ClickEvent event) {
        showFancyScreen("Hello!!!");
    }

    @Subscribe("showOtherScreenBtn")
    public void onShowOtherScreenBtnClick(Button.ClickEvent event) {
        openOtherScreen();
    }

    @Subscribe("showNewOtherScreenBtn")
    public void onShowNewOtherScreenBtnClick(Button.ClickEvent event) {
        openNewOtherScreen();
    }
}