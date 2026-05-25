package com.company.pivottableonboarding.view.tipsanalyse;


import com.company.pivottableonboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "tips-analyse-view", layout = MainView.class)
@ViewController(id = "TipsAnalyseView")
@ViewDescriptor(path = "tips-analyse-view.xml")
public class TipsAnalyseView extends StandardView {
}