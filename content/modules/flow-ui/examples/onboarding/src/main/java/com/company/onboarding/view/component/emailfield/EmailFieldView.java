package com.company.onboarding.view.component.emailfield;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "EmailFieldView", layout = MainView.class)
@ViewController("EmailFieldView")
@ViewDescriptor("emailField-view.xml")
public class EmailFieldView extends StandardView {
}