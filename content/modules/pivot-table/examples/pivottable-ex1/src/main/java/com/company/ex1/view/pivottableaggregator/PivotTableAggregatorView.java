package com.company.ex1.view.pivottableaggregator;


import com.company.ex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "pivot-table-aggregator-view", layout = MainView.class)
@ViewController(id = "PivotTableAggregatorView")
@ViewDescriptor(path = "pivot-table-aggregator-view.xml")
public class PivotTableAggregatorView extends StandardView {
}