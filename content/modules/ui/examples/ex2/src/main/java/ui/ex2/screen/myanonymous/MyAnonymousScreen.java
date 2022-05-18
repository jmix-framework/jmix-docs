package ui.ex2.screen.myanonymous;

import io.jmix.ui.Screens;
import io.jmix.ui.UiProperties;
import io.jmix.ui.component.Button;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

// tag::my-anonymous-screen[]
@UiController("MyAnonymousScreen")
@UiDescriptor("my-anonymous-screen.xml")
@Route(path = "anonymous") // <1>
public class MyAnonymousScreen extends Screen {
    @Autowired
    private Screens screens;

    @Autowired
    private UiProperties uiProperties;

    @Subscribe("showLoginScreenBtn")
    protected void onShowLoginScreenBtnClick(Button.ClickEvent event) {
        String loginScreenId = uiProperties.getLoginScreenId();
        Screen loginScreen = screens.create(loginScreenId, OpenMode.ROOT);
        loginScreen.show();
    }
}
// end::my-anonymous-screen[]