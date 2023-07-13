package com.company.onboarding.view.component.multiselectcombobox;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.shared.HasClientValidation;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "MultiSelectComboBoxView", layout = MainView.class)
@ViewController("MultiSelectComboBoxView")
@ViewDescriptor("multi-select-combo-box-view.xml")
public class MultiSelectComboBoxView extends StandardView {
    @Subscribe("multiSelectComboBox")
    public void onMultiSelectComboBoxClientValidated(final HasClientValidation.ClientValidatedEvent event) {
        
    }
}