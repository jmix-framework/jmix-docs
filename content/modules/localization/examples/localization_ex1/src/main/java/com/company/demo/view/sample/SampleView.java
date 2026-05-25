package com.company.demo.view.sample;


import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "SampleView", layout = MainView.class)
@ViewController("SampleView")
@ViewDescriptor("sample-view.xml")
public class SampleView extends StandardView {
}