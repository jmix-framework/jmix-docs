package com.company.onboarding.view.component.datagrid;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "data-grid-features-view", layout = MainView.class)
@ViewController("DataGridFeaturesView")
@ViewDescriptor("data-grid-features-view.xml")
public class DataGridFeaturesView extends StandardView {
}