package ui.ex2.bean;

import io.jmix.core.common.util.ParamsMap;
import io.jmix.ui.AppUI;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.WebBrowserTools;
import io.jmix.ui.screen.Screen;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ui.ex2.screen.city.CityBrowse;

import java.util.Map;

// tag::start[]
@Component("sample_MenuBean")
public class MenuBean {
    // end::start[]
    // tag::menu-bean[]
    @Autowired
    private ScreenBuilders screenBuilders;

    public void showCityBrowse(){
        final Screen frameOwner = AppUI.getCurrent()
                .getTopLevelWindowNN().getFrameOwner();
        screenBuilders.screen(frameOwner)
                .withScreenClass(CityBrowse.class)
                .build()
                .show();
    }
    // end::menu-bean[]
    // tag::method-with-params[]

    @Autowired
    private ObjectProvider<WebBrowserTools> webBrowserToolsProvider;

    public void openLink(Map<String, Object> parameters) {
        String url = (String) parameters.get("url");
        if (url == null) {
            return;
        }

        webBrowserToolsProvider.getObject().showWebPage(
                url, ParamsMap.of("target", "_blank"));
    }
    // end::method-with-params[]
    // tag::end[]
}
// end::end[]