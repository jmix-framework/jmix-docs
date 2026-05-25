package com.company.onboarding.view.address.var3;

import com.company.onboarding.entity.City;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.View;
import io.jmix.flowui.view.ViewComponent;

// tag::fragment-java[]
@FragmentDescriptor("address-fragment.xml")
public class AddressFragment extends Fragment<FormLayout> {

    @ViewComponent
    private CollectionLoader<City> citiesDl;

    @Subscribe(target = Target.HOST_CONTROLLER)
    protected void onHostBeforeShow(View.BeforeShowEvent event) { // <1>
        citiesDl.load();
    }
}
// end::fragment-java[]