package com.company.onboarding.view.component.multivaluepicker;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "MultiValuePickerView", layout = MainView.class)
@ViewController("MultiValuePickerView")
@ViewDescriptor("multi-value-picker-view.xml")
public class MultiValuePickerView extends StandardView {
}