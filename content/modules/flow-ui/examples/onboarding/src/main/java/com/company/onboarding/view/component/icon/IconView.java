package com.company.onboarding.view.component.icon;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "IconView", layout = MainView.class)
@ViewController("IconView")
@ViewDescriptor("icon-view.xml")
public class IconView extends StandardView {

}