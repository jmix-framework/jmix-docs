package com.company.onboarding.view.customcomponent.jscomponent;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "js-component-view", layout = MainView.class)
@ViewController("JsComponentView")
@ViewDescriptor("js-component-view.xml")
public class JsComponentView extends StandardView {
    @Subscribe
    public void onInit(final InitEvent event) {
        getContent().add(new Span("Slider demo is temporarily disabled"));
    }
}
