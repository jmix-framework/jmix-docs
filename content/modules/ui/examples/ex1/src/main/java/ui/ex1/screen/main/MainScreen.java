package ui.ex1.screen.main;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.SideMenu;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("uiex1_MainScreen")
@UiDescriptor("main-screen.xml")
public class MainScreen extends Screen implements Window.HasWorkArea {

    // tag::inject-for-menu[]
    @Autowired
    private SideMenu sideMenu;
    @Autowired
    private Notifications notifications;

    // end::inject-for-menu[]

    @Autowired
    private AppWorkArea workArea;

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
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
    @Subscribe("sideMenu")
    public void onSideMenuItemSelect(SideMenu.ItemSelectEvent event) {
        notifications.create()
                .withCaption("ItemSelectEvent is fired for " + event.getMenuItem().getCaption())
                .withType(Notifications.NotificationType.HUMANIZED)
                .show();
    }

}
