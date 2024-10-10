package com.company.onboarding.view.sampleone;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "sample-one", layout = MainView.class)
@ViewController("SampleOne")
@ViewDescriptor("sample-one.xml")
public class SampleOne extends StandardView {
}