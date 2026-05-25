package com.company.onboarding.view.customer;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "customer-fragment-host-view", layout = MainView.class)
@ViewController("CustomerFragmentHostView")
@ViewDescriptor("customer-fragment-host-view.xml")
public class CustomerFragmentHostView extends StandardView {
}