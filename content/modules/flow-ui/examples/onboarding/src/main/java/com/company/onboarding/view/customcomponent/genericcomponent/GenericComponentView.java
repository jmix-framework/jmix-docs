package com.company.onboarding.view.customcomponent.genericcomponent;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "generic-component-view", layout = MainView.class)
@ViewController(id = "GenericComponentView")
@ViewDescriptor(path = "generic-component-view.xml")
public class GenericComponentView extends StandardView {

    @Autowired
    protected Notifications notifications;

    // Shows that a generic component can participate in standard declarative event wiring.
    @Subscribe(id = "button", subject = "clickListener")
    public void onButtonClick(ClickEvent<JmixButton> event) {
        notifications.show("Saved!");
    }
}
