package com.company.mapssample.view.mapbasic;


import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "map-basic-view", layout = MainView.class)
@ViewController("MapBasicView")
@ViewDescriptor("map-basic-view.xml")
public class MapBasicView extends StandardView {
}