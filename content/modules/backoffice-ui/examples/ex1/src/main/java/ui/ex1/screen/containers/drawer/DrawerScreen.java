package ui.ex1.screen.containers.drawer;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_DrawerScreen")
@UiDescriptor("drawer-screen.xml")
public class DrawerScreen extends Screen {
    // tag::drawer[]
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;

    @Subscribe("collapseDrawerButton")
    public void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle(); // <1>
        if (drawer.isCollapsed()) { // <2>
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }
    // end::drawer[]
}