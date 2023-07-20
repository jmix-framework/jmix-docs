package com.company.onboarding.view.component.propertyfilter;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.view.*;

@Route(value = "PropertyFilterView", layout = MainView.class)
@ViewController("PropertyFilterView")
@ViewDescriptor("property-filter-view.xml")
public class PropertyFilterView extends StandardView {

    // tag::validator[]
    @Install(to = "propertyFilter", subject = "validator")
    private void propertyFilterValidator(String value) {
        if (value != null && value.length() != 6)
            throw new ValidationException("Zip must be of 6 digits length");
    }
    // end::validator[]
}