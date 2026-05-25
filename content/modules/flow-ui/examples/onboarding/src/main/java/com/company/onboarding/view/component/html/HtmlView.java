package com.company.onboarding.view.component.html;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.router.Route;
import io.jmix.core.Resources;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

@Route(value = "html-view", layout = MainView.class)
@ViewController("HtmlView")
@ViewDescriptor("html-view.xml")
public class HtmlView extends StandardView {

    //tag::stream[]
    protected static final String SRC_PATH = "META-INF/resources/html/html-file.html";
    @Autowired
    private Resources resources;

    @Subscribe
    public void onInit(final InitEvent event) {
        InputStream resourceAsStream = resources.getResourceAsStream(SRC_PATH);
        Html html = new Html(resourceAsStream);
    }
    //end::stream[]
}