package com.company.onboarding.view.component.textfield;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "TextFieldView", layout = MainView.class)
@ViewController("TextFieldView")
@ViewDescriptor("text-field-view.xml")
public class TextFieldView extends StandardView {
}