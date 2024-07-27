package com.company.onboarding.view.main;

import com.company.onboarding.event.OnboardingStatusChangedEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.main.JmixListMenu;
import io.jmix.flowui.kit.component.main.ListMenu;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.context.event.EventListener;

import java.util.random.RandomGenerator;

@Route("")
@ViewController("MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {

    // tag::menu-component[]
    @ViewComponent
    private JmixListMenu menu;

    // end::menu-component[]

    // tag::initialLayout[]
    @ViewComponent
    private JmixImage<Object> urlImage;

    // tag::init-event[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::init-initialLayout[]
        urlImage.setSrc("https://www.jmix.io/uploads/framework_image_9efadbc372.svg");
        urlImage.setWidth("100%");
        urlImage.setHeight("100%");
        // end::init-initialLayout[]
        // tag::init-onBoardingStatus[]
        updateOnboardingStatus(); // <1>
        // end::init-onBoardingStatus[]
    }
    // end::init-event[]

    @Subscribe(id = "urlImage", subject = "singleClickListener")
    public void onUrlImageClick(final ClickEvent<JmixImage<?>> event) {
        Notification.show("Clicked!");
    }
    // end::initialLayout[]

    // tag::ui-event-listener[]

    @EventListener
    private void onBoardingStatusChanged(OnboardingStatusChangedEvent event) { // <2>
        updateOnboardingStatus();
    }

    private void updateOnboardingStatus() {
        long number = getUncompletedStepsNumber(); // <3>

        Span badge = null; // <4>
        if (number > 0) {
            badge = new Span("" + number);
            badge.getElement().getThemeList().add("badge warning");
        }

        ListMenu.MenuItem menuItem = menu.getMenuItem("MyOnboardingView");
        // Can be 'null' if menu item isn't permitted by security
        if (menuItem != null) {
            menuItem.setSuffixComponent(badge);
        }
    }
    // end::ui-event-listener[]

    private long getUncompletedStepsNumber() {
        return RandomGenerator.getDefault().nextInt(1, 5);
    }
}
