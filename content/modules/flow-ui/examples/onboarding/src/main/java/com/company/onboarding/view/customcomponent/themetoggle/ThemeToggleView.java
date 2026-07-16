package com.company.onboarding.view.customcomponent.themetoggle;


import com.company.onboarding.component.ThemeToggle;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "theme-toggle-view", layout = MainView.class)
@ViewController("ThemeToggleView")
@ViewDescriptor("theme-toggle-view.xml")
public class ThemeToggleView extends StandardView {
    @Autowired
    protected Notifications notifications;

    // tag::component-usage[]
    @Subscribe
    public void onInit(final InitEvent event) {
        ThemeToggle themeToggle = new ThemeToggle();
        themeToggle.setText("Click to switch theme");
        themeToggle.addThemeChangeListener(changedEvent ->
                notifications.create("Theme switched: " + getThemeValue(changedEvent))
                        .withPosition(Notification.Position.TOP_CENTER)
                        .show());
        getContent().add(themeToggle);
    }
    // end::component-usage[]

    protected String getThemeValue(ThemeToggle.ThemeToggleThemeChangedEvent event) {
        return event.getValue().isEmpty() ? "Light" : "Dark";
    }
}
