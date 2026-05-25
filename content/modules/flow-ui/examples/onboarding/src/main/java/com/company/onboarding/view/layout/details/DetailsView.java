package com.company.onboarding.view.layout.details;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "DetailsView", layout = MainView.class)
@ViewController("DetailsView")
@ViewDescriptor("details-view.xml")
public class DetailsView extends StandardView {
}