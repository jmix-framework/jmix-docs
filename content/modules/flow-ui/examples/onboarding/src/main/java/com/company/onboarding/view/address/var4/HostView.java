package com.company.onboarding.view.address.var4;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

// tag::fragment-in-view[]
@Route(value = "HostView4", layout = MainView.class)
@ViewController("HostView4")
@ViewDescriptor("host-view.xml")
public class HostView extends StandardView {
}
// end::fragment-in-view[]