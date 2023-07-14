package com.company.onboarding.view.component.multiselectlistbox;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "MultiSelectListBoxView", layout = MainView.class)
@ViewController("MultiSelectListBoxView")
@ViewDescriptor("multi-select-list-box-view.xml")
public class MultiSelectListBoxView extends StandardView {
}