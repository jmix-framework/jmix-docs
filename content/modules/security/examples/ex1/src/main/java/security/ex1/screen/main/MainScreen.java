package security.ex1.screen.main;

import io.jmix.core.usersubstitution.CurrentUserSubstitution;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import security.ex1.entity.User;

@UiController("sample_MainScreen")
@UiDescriptor("main-screen.xml")
public class MainScreen extends Screen implements Window.HasWorkArea {

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle();
        if (drawer.isCollapsed()) {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }

    // tag::user-substitution[]
    @Autowired
    private CurrentUserSubstitution currentUserSubstitution;

    private String getSubstitutedUserName() {
        User substitutedUser = (User) currentUserSubstitution.getSubstitutedUser();
        return substitutedUser == null ? "" : substitutedUser.getUsername();
    }
    // end::user-substitution[]
}
