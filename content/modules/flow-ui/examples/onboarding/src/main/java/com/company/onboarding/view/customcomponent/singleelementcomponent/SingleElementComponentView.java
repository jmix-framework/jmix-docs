package com.company.onboarding.view.customcomponent.singleelementcomponent;


import com.company.onboarding.component.ColorPicker;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "single-element-component-view", layout = MainView.class)
@ViewController("SingleElementComponentView")
@ViewDescriptor("single-element-component-view.xml")
public class SingleElementComponentView extends StandardView {

    // tag::color-picker-usage[]
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInit(final InitEvent event) {
        ColorPicker colorPicker = new ColorPicker();
        getContent().add(colorPicker);

        colorPicker.addValueChangeListener(e ->
                notifications.show("Color: " + e.getValue()));
    }
    // end::color-picker-usage[]
}