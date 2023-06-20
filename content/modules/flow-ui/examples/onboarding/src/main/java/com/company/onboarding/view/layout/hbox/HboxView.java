package com.company.onboarding.view.layout.hbox;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "HboxView", layout = MainView.class)
@ViewController("HboxView")
@ViewDescriptor("hbox-view.xml")
public class HboxView extends StandardView {

}