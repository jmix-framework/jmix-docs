package com.company.ex1.view.pivottableshowaction;


import com.company.ex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "pivottable-show-action-view", layout = MainView.class)
@ViewController(id = "PivottableShowActionView")
@ViewDescriptor(path = "pivottable-show-action-view.xml")
public class PivottableShowActionView extends StandardView {
}