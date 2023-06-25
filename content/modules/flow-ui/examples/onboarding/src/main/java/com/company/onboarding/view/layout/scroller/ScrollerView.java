package com.company.onboarding.view.layout.scroller;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "ScrollerView", layout = MainView.class)
@ViewController("ScrollerView")
@ViewDescriptor("scroller-view.xml")
public class ScrollerView extends StandardView {
}