package ui.ex1.screen.main;

import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.component.AppWorkArea;
import io.jmix.ui.component.Window;
import io.jmix.ui.component.mainwindow.AppMenu;
import io.jmix.ui.component.mainwindow.UserIndicator;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiControllerUtils;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

// tag::main-top-start[]
@UiController("MainTop")
@UiDescriptor("main-screen-top-menu.xml")
@Route(path = "main", root = true)
public class MainScreenTopMenu extends Screen implements Window.HasWorkArea {
    // end::main-top-start[]

    // tag::user-indicator[]
    @Autowired
    private UserIndicator userIndicator;

    // end::user-indicator[]

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;
    // tag::inject-for-menu[]
    @Autowired
    private AppMenu mainMenu;
    @Autowired
    private Notifications notifications;

    // end::inject-for-menu[]

    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());

        screenTools.handleRedirect();
    }

    // tag::formatter[]
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        userIndicator.setFormatter(value -> value.getUsername() + " - user");
    }
// end::formatter[]

    // tag::on-init-for-menu[]
    @Subscribe
    public void onInit(InitEvent event) {
        AppMenu.MenuItem rootItem = mainMenu.createMenuItem("help", "Help");
        AppMenu.MenuItem subItemStarted = mainMenu.createMenuItem("start",
                "Getting Started");
        AppMenu.MenuItem subItemNews = mainMenu.createMenuItem("news", "News",
                "font-icon:NEWSPAPER_O", null); // <1>
        AppMenu.MenuItem subItemBlog = mainMenu.createMenuItem("blog", "Blog");
        AppMenu.MenuItem subItemSite = mainMenu.createMenuItem("site", "Website");
        AppMenu.MenuItem subItemAbout = mainMenu.createMenuItem("about", "About",
                null, menuItem -> { // <2>
            notifications.create()
                    .withCaption("About menu item clicked")
                    .withType(Notifications.NotificationType.HUMANIZED)
                    .show();
        });
        AppMenu.MenuItem subItemCenter = mainMenu.createMenuItem("center",
                "Client Center");

        subItemNews.addChildItem(subItemBlog);
        subItemNews.addChildItem(subItemSite);

        rootItem.addChildItem(subItemStarted);
        rootItem.addChildItem(subItemNews);
        rootItem.addChildItem(subItemAbout);
        rootItem.addChildItem(mainMenu.createSeparator()); // <3>
        rootItem.addChildItem(subItemCenter);

        mainMenu.addMenuItem(rootItem, 0);
    }
    // end::on-init-for-menu[]
    // tag::main-top-end[]
}
// end::main-top-end[]