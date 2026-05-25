package com.company.onboarding.view.component.multiselectcomboboxpicker;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "multi-select-combo-box-picker-view", layout = MainView.class)
@ViewController(id = "MultiSelectComboBoxPickerView")
@ViewDescriptor(path = "multi-select-combo-box-picker-view.xml")
public class MultiSelectComboBoxPickerView extends StandardView {
}