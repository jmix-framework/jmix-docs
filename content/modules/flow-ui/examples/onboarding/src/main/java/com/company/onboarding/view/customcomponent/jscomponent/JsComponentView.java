package com.company.onboarding.view.customcomponent.jscomponent;


import com.company.onboarding.component.Slider;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "js-component-view", layout = MainView.class)
@ViewController("JsComponentView")
@ViewDescriptor("js-component-view.xml")
public class JsComponentView extends StandardView {
    // tag::js-component-usage[]
    @Autowired
    protected Notifications notifications;

    @Subscribe
    public void onInit(final InitEvent event) {
        Slider slider = new Slider();
        slider.setWidth("20em");
        slider.setMin(10);
        slider.setMax(100);

        slider.addValueChangeListener(changedEvent ->
                notifications.create("New value is : " + changedEvent.getValue())
                        .withPosition(Notification.Position.MIDDLE)
                        .show());

        getContent().add(slider);
    }
    // end::js-component-usage[]
}