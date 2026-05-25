package com.company.onboarding.view.layout.formlayout;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "FormLayoutView", layout = MainView.class)
@ViewController("FormLayoutView")
@ViewDescriptor("form-layout-view.xml")
public class FormLayoutView extends StandardView {

}