package com.company.ex1.view.pivottablerenderer;


import com.company.ex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "pivot-table-renderer-view", layout = MainView.class)
@ViewController(id = "PivotTableRendererView")
@ViewDescriptor(path = "pivot-table-renderer-view.xml")
public class PivotTableRendererView extends StandardView {
}