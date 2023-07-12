package com.company.onboarding.view.component.combobutton;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "ComboButtonView", layout = MainView.class)
@ViewController("ComboButtonView")
@ViewDescriptor("combo-button-view.xml")
public class ComboButtonView extends StandardView {
}