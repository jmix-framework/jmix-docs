package com.company.demo.view.search;


import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "filter-view", layout = MainView.class)
@ViewController("FilterView")
@ViewDescriptor("filter-view.xml")
public class FilterView extends StandardView {
}