package com.company.onboarding.view.customcomponent.compositecomponent;


import com.company.onboarding.component.ColorField;
import com.company.onboarding.component.ColorComponent;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "composite-component-view", layout = MainView.class)
@ViewController("CompositeComponentView")
@ViewDescriptor("composite-component-view.xml")
@AnonymousAllowed
public class CompositeComponentView extends StandardView {

    // tag::composite-component-view[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::composite-component[]
        ColorComponent colorComponent = new ColorComponent();
        getContent().add(colorComponent);
        // end::composite-component[]
        // tag::custom-field[]
        ColorField customField = new ColorField();
        customField.setLabel("Color Field");
        customField.setHelperText("Helper text");
        getContent().add(customField);
        // end::custom-field[]
    }
    // end::composite-component-view[]
}