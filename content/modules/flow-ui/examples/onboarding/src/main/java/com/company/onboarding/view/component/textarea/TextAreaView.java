package com.company.onboarding.view.component.textarea;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "TextAreaView", layout = MainView.class)
@ViewController("TextAreaView")
@ViewDescriptor("text-area-view.xml")
public class TextAreaView extends StandardView {

}