package ui.ex1.screen.main;

import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.component.mainwindow.SideMenu;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

// tag::main-top-start[]
@UiController("MainScreenSideMenu")
@UiDescriptor("main-screen-side-menu.xml")
@Route(path = "main", root = true)
public class MainScreenSideMenu extends Screen implements Window.HasWorkArea {
    // end::main-top-start[]

    @Autowired
    private ScreenTools screenTools;
    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;
    // tag::inject-for-menu[]
    @Autowired
    private SideMenu sideMenu;
    @Autowired
    private Notifications notifications;

    // end::inject-for-menu[]

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

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
    }
    // tag::on-init-for-menu[]
    @Subscribe
    public void onInit(InitEvent event) {
        SideMenu.MenuItem rootItem = sideMenu.createMenuItem("help", "Help");
        SideMenu.MenuItem subItemStarted = sideMenu.createMenuItem("start",
                "Getting Started");
        SideMenu.MenuItem subItemNews = sideMenu.createMenuItem("news", "News",
                "font-icon:NEWSPAPER_O", null); // <1>
        SideMenu.MenuItem subItemBlog = sideMenu.createMenuItem("blog", "Blog");
        SideMenu.MenuItem subItemSite = sideMenu.createMenuItem("site", "Website");
        SideMenu.MenuItem subItemAbout = sideMenu.createMenuItem("about", "About",
                null, menuItem -> { // <2>
                    notifications.create()
                            .withCaption("About menu item clicked")
                            .withType(Notifications.NotificationType.HUMANIZED)
                            .show();
                });
        SideMenu.MenuItem subItemCenter = sideMenu.createMenuItem("center",
                "Client Center");

        subItemAbout.setBadgeText("NEW"); // <3>

        subItemNews.addChildItem(subItemBlog);
        subItemNews.addChildItem(subItemSite);

        rootItem.addChildItem(subItemStarted);
        rootItem.addChildItem(subItemNews);
        rootItem.addChildItem(subItemAbout);
        rootItem.addChildItem(subItemCenter);

        sideMenu.addMenuItem(rootItem, 0);
    }
    // end::on-init-for-menu[]
    // tag::item-select-event[]
    @Subscribe("sideMenu")
    public void onSideMenuItemSelect(SideMenu.ItemSelectEvent event) {
        notifications.create()
                .withCaption("ItemSelectEvent is fired for "
                        + event.getMenuItem().getCaption())
                .withType(Notifications.NotificationType.HUMANIZED)
                .show();
    }
// end::item-select-event[]

// tag::main-top-end[]
}
// end::main-top-end[]