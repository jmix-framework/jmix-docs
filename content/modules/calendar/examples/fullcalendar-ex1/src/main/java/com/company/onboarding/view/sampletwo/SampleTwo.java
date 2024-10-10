package com.company.onboarding.view.sampletwo;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "sample-two", layout = MainView.class)
@ViewController("SampleTwo")
@ViewDescriptor("sample-two.xml")
public class SampleTwo extends StandardView {
}