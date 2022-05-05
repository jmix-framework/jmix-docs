package ui.ex2.bean;

import io.jmix.ui.AppUI;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.screen.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ui.ex2.screen.city.CityBrowse;

// tag::menu-bean[]
@Component("sample_MenuBean")
public class MenuBean {

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
}
// end::menu-bean[]