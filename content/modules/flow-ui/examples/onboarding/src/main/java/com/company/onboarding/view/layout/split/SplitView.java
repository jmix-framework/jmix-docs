package com.company.onboarding.view.layout.split;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "SplitView", layout = MainView.class)
@ViewController("SplitView")
@ViewDescriptor("split-view.xml")
public class SplitView extends StandardView {

}