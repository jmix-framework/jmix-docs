package com.company.fullcalendarsample.view.demos;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "sample-basic", layout = MainView.class)
@ViewController("SampleBasic")
@ViewDescriptor("sample-basic.xml")
public class SampleBasic extends StandardView {
}