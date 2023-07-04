package com.company.onboarding.view.htmlcomponent.div;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "DivView", layout = MainView.class)
@ViewController("DivView")
@ViewDescriptor("div-view.xml")
public class DivView extends StandardView {
}