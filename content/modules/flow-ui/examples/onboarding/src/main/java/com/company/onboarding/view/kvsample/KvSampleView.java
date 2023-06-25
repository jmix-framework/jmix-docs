package com.company.onboarding.view.kvsample;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "KvSampleView", layout = MainView.class)
@ViewController("KvSampleView")
@ViewDescriptor("kv-sample-view.xml")
public class KvSampleView extends StandardView {
}