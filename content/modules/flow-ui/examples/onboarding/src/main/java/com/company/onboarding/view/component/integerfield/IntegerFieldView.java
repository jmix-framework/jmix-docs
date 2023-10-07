package com.company.onboarding.view.component.integerfield;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "IntegerFieldView", layout = MainView.class)
@ViewController("IntegerFieldView")
@ViewDescriptor("integerfield-view.xml")
public class IntegerFieldView extends StandardView {
}