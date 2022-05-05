package ui.ex2.app;

import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.WebBrowserTools;
import io.jmix.ui.menu.MenuItem;
import io.jmix.ui.menu.MenuItemRunnable;
import io.jmix.ui.screen.FrameOwner;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// tag::custom-menu[]
public class CustomMenu implements MenuItemRunnable, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void openLink(String url) {
        applicationContext.getBean(WebBrowserTools.class)
                .showWebPage(url, ParamsMap.of("target", "_blank"));
    }

    @Override
    public void run(FrameOwner origin, MenuItem menuItem) {
        if (menuItem.getId().equals("jmix")) {
            openLink("https://jmix.io");
        } else {
            openLink("https://google.com");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
// end::custom-menu[]