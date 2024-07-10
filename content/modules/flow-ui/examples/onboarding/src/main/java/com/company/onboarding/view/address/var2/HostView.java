package com.company.onboarding.view.address.var2;

import com.company.onboarding.entity.City;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Fragments;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "HostView2", layout = MainView.class)
@ViewController("HostView2")
@ViewDescriptor("host-view.xml")
public class HostView extends StandardView {
// tag::fragment-in-view[]
    @ViewComponent
    private CollectionContainer<City> citiesDc;

    @Autowired
    private Fragments fragments;

    @Subscribe
    public void onInit(InitEvent event) {
        AddressFragment addressFragment = fragments.create(this, AddressFragment.class);
        addressFragment.setCitiesContainer(citiesDc); // <1>
        addressFragment.setZipcodePlaceholder("Zipcode");
        getContent().add(addressFragment);
    }
// end::fragment-in-view[]
}