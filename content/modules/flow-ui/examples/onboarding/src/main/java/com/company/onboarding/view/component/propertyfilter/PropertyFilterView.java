package com.company.onboarding.view.component.propertyfilter;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.propertyfilter.PropertyFilter;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "PropertyFilterView", layout = MainView.class)
@ViewController("PropertyFilterView")
@ViewDescriptor("property-filter-view.xml")
public class PropertyFilterView extends StandardView {

    @Autowired
    private Notifications notifications;

    @Install(to = "propertyFilter_2", subject = "validator")
    private void propertyFilter_2Validator(String value) {
        if (value != null && value.length() != 6)
            throw new ValidationException("Zip must be of 6 digits length");
    }
}