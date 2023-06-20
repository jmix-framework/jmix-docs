package com.company.onboarding.view.layout.tabsheet;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "TabsheetView", layout = MainView.class)
@ViewController("TabsheetView")
@ViewDescriptor("tabsheet-view.xml")
public class TabSheetView extends StandardView {

}