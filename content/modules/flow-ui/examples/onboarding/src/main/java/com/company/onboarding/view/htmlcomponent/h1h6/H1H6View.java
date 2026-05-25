package com.company.onboarding.view.htmlcomponent.h1h6;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "H1H6View", layout = MainView.class)
@ViewController("H1H6View")
@ViewDescriptor("h1-h6-view.xml")
public class H1H6View extends StandardView {
}