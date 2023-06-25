package com.company.onboarding.view.actions;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "StandardPickerActionsView", layout = MainView.class)
@ViewController("StandardPickerActionsView")
@ViewDescriptor("standard-picker-actions-view.xml")
public class StandardPickerActionsView extends StandardView {
}