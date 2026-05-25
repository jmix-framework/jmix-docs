package com.company.onboarding.view.address.var1;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Fragments;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

// tag::fragment-in-view[]
@Route(value = "HostView", layout = MainView.class)
@ViewController("HostView")
@ViewDescriptor("host-view.xml")
public class HostView extends StandardView {

    @ViewComponent
    private Details addressDetails;

    @Autowired
    private Fragments fragments; // <1>

    @Subscribe
    public void onInit(InitEvent event) {
        AddressFragment addressFragment = fragments.create(this, AddressFragment.class); // <2>
        addressDetails.add(addressFragment); // <3>
    }
}
// end::fragment-in-view[]