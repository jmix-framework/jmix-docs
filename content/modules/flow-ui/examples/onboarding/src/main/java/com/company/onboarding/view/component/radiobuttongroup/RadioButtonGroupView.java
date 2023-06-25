package com.company.onboarding.view.component.radiobuttongroup;


import com.company.onboarding.entity.OnboardingStatus;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.shared.HasClientValidation;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.radiobuttongroup.JmixRadioButtonGroup;
import io.jmix.flowui.view.*;

@Route(value = "RadioButtonGroupView", layout = MainView.class)
@ViewController("RadioButtonGroupView")
@ViewDescriptor("radio-button-group-view.xml")
public class RadioButtonGroupView extends StandardView {

    // tag::itemEnabledProvider[]
    @Install(to = "radioButtonGroup", subject = "itemEnabledProvider")
    private boolean radioButtonGroupItemEnabledProvider(OnboardingStatus onboardingStatus) {
        if (onboardingStatus != null) {
            return onboardingStatus.getId() != 30;
        }
        return true;
    }
    // end::itemEnabledProvider[]
}