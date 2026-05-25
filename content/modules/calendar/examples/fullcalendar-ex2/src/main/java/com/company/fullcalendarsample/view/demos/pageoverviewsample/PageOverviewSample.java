package com.company.fullcalendarsample.view.demos.pageoverviewsample;


import com.company.fullcalendarsample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "page-overview-sample", layout = MainView.class)
@ViewController("PageOverviewSample")
@ViewDescriptor("page-overview-sample.xml")
public class PageOverviewSample extends StandardView {
}