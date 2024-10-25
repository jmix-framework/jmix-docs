package com.company.fullcalendarsample.view.demos;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "url-query-parameters-view", layout = MainView.class)
@ViewController(id = "UrlQueryParametersView")
@ViewDescriptor(path = "url-query-parameters-view.xml")
public class UrlQueryParametersView extends StandardView {
}