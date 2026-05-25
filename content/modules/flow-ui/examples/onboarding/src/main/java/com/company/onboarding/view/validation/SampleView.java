package com.company.onboarding.view.validation;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "ValidationSampleView", layout = MainView.class)
@ViewController("ValidationSampleView")
@ViewDescriptor("sample-view.xml")
// tag::validateUiComponents[]
public class SampleView extends StandardView {

    @Autowired
    private ViewValidation viewValidation;

    @ViewComponent
    private FormLayout form;

    @Subscribe("validateBtn")
    public void onValidateBtnClick(final ClickEvent<JmixButton> event) {
        ValidationErrors errors = viewValidation.validateUiComponents(form);
        if (!errors.isEmpty()) {
            viewValidation.showValidationErrors(errors);
        }
    }
}
// end::validateUiComponents[]