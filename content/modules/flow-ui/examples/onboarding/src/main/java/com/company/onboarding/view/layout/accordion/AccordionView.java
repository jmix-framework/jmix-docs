package com.company.onboarding.view.layout.accordion;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "AccordionView", layout = MainView.class)
@ViewController("AccordionView")
@ViewDescriptor("accordion-view.xml")
public class AccordionView extends StandardView {

}