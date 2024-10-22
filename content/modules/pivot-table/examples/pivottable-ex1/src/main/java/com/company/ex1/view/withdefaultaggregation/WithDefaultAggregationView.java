package com.company.ex1.view.withdefaultaggregation;


import com.company.ex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "with-default-aggregation-view", layout = MainView.class)
@ViewController(id = "WithDefaultAggregationView")
@ViewDescriptor(path = "with-default-aggregation-view.xml")
public class WithDefaultAggregationView extends StandardView {
}