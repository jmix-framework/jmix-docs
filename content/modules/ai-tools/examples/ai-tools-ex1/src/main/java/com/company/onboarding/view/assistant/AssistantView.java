package com.company.onboarding.view.assistant;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "assistant", layout = MainView.class)
@ViewController("AssistantView")
@ViewDescriptor("assistant-view.xml")
public class AssistantView extends StandardView {
}
