package com.company.onboarding.view.layout.formlayout;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "FormLayoutView", layout = MainView.class)
@ViewController("FormLayoutView")
@ViewDescriptor("form-layout-view.xml")
public class FormLayoutView extends StandardView {

    // tag::formLayout[]
    @ViewComponent
    private FormLayout formLayout;

    // end::formLayout[]

    // tag::responsiveSteps[]
    @Subscribe
    public void onInit(final InitEvent event) {
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("320px", 2),
                new FormLayout.ResponsiveStep("460px", 3),
                new FormLayout.ResponsiveStep("600px", 4),
                new FormLayout.ResponsiveStep("740px", 5));
    }
    // end::responsiveSteps[]

}