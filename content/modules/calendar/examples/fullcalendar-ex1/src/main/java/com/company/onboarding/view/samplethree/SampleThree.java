package com.company.onboarding.view.samplethree;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "sample-three", layout = MainView.class)
@ViewController("SampleThree")
@ViewDescriptor("sample-three.xml")
public class SampleThree extends StandardView {
}