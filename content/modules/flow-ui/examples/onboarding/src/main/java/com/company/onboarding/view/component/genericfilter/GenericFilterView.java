package com.company.onboarding.view.component.genericfilter;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "GenericFilterView", layout = MainView.class)
@ViewController("GenericFilterView")
@ViewDescriptor("generic-filter-view.xml")
public class GenericFilterView extends StandardView {

}