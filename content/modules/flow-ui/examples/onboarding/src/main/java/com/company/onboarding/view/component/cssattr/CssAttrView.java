package com.company.onboarding.view.component.cssattr;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "css-attr-view", layout = MainView.class)
@ViewController("CssAttrView")
@ViewDescriptor("css-attr-view.xml")
public class CssAttrView extends StandardView {
}