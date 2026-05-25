package com.company.onboarding.view.layout.flexlayout;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "flex-layout-view", layout = MainView.class)
@ViewController("FlexLayoutView")
@ViewDescriptor("flex-layout-view.xml")
public class FlexLayoutView extends StandardView {
}