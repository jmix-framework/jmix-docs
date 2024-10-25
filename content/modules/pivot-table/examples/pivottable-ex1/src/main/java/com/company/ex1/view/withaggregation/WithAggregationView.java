package com.company.ex1.view.withaggregation;


import com.company.ex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "with-aggregation-view", layout = MainView.class)
@ViewController(id = "WithAggregationView")
@ViewDescriptor(path = "with-aggregation-view.xml")
public class WithAggregationView extends StandardView {
}