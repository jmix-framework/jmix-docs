package com.company.onboarding.view.customcomponent.themetoggle;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "theme-toggle-view", layout = MainView.class)
@ViewController("ThemeToggleView")
@ViewDescriptor("theme-toggle-view.xml")
public class ThemeToggleView extends StandardView {
    @Subscribe
    public void onInit(final InitEvent event) {
        getContent().add(new Span("Theme toggle demo is temporarily disabled"));
    }
}
