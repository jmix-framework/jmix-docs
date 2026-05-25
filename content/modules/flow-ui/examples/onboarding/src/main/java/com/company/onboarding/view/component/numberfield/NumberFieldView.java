package com.company.onboarding.view.component.numberfield;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "NumberFieldView", layout = MainView.class)
@ViewController("NumberFieldView")
@ViewDescriptor("numberfield-view.xml")
public class NumberFieldView extends StandardView {
}