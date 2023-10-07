package com.company.onboarding.view.component.bigdecimalfield;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "BigDecimalFieldView", layout = MainView.class)
@ViewController("BigDecimalFieldView")
@ViewDescriptor("bigDecimalField-view.xml")
public class BigDecimalFieldView extends StandardView {
}