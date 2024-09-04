package com.company.onboarding.app;

import com.company.onboarding.view.city.CityListView;
import com.vaadin.flow.component.UI;
import io.jmix.flowui.view.navigation.ViewNavigationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


// tag::start[]
@Component("MenuBean")
public class MenuBean {
    // end::start[]

    // tag::menu-bean[]

    @Autowired
    private ViewNavigationSupport viewNavigationSupport;

    public void browseCities() {
        viewNavigationSupport.navigate(CityListView.class);
    }
    // end::menu-bean[]

    // tag::method-with-params[]

    public void openLink(Map<String, Object> parameters) {
        String url = (String) parameters.get("url");
        if (url == null) {
            return;
        }
        UI.getCurrent().getPage().open(url);
    }
    // end::method-with-params[]

    // tag::end[]
}
// end::end[]