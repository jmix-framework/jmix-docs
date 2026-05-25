package com.company.onboarding.view.htmlcomponent.span;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "SpanView", layout = MainView.class)
@ViewController("SpanView")
@ViewDescriptor("span-view.xml")
public class SpanView extends StandardView {

}