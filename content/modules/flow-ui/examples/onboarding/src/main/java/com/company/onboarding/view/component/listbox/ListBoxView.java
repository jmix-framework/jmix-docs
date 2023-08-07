package com.company.onboarding.view.component.listbox;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "ListBoxView", layout = MainView.class)
@ViewController("ListBoxView")
@ViewDescriptor("list-box-view.xml")
public class ListBoxView extends StandardView {
}