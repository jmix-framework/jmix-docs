package ui.ex1.screen.containers.layout;

import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_LayoutScreen")
@UiDescriptor("layout-screen.xml")
public class LayoutScreen extends Screen {
    @Autowired
    private Screens screens;
    @Subscribe("open1")
    public void onOpen1Click(Button.ClickEvent event) {
        screens.create(LayoutScreen1.class).show();
    }

    @Subscribe("open2")
    public void onOpen2Click(Button.ClickEvent event) {
        screens.create(LayoutScreen2.class).show();
    }
}