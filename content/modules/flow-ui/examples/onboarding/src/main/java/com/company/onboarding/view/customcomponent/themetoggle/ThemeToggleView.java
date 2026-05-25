package com.company.onboarding.view.customcomponent.themetoggle;


import com.company.onboarding.component.ThemeToggle;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "theme-toggle-view", layout = MainView.class)
@ViewController("ThemeToggleView")
@ViewDescriptor("theme-toggle-view.xml")
public class ThemeToggleView extends StandardView {
    // tag::component-usage[]
    @Subscribe
    public void onInit(final InitEvent event) {
        ThemeToggle themeToggle = new ThemeToggle();
        getContent().add(themeToggle);
    }
    // end::component-usage[]
}