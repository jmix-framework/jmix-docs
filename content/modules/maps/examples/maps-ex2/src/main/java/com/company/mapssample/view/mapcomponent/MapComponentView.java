package com.company.mapssample.view.mapcomponent;


import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "map-component-view", layout = MainView.class)
@ViewController(id = "MapComponentView")
@ViewDescriptor(path = "map-component-view.xml")
public class MapComponentView extends StandardView {
}