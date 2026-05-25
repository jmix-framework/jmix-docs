package com.company.onboarding.view.customer;

import com.company.onboarding.entity.Customer;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.Renderer;
import io.jmix.core.LoadContext;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;

import java.util.List;

public class CustomerListFragment2 extends Fragment<VerticalLayout> {

    // tag::autowire[]
    @ViewComponent
    public JmixButton button; // <1>

    @ViewComponent
    public CollectionContainer<Customer> collectionDc; // <2>

    @Subscribe
    public void onReady(ReadyEvent event) { // <3>
        // ...
    }

    @Subscribe(value = "button", subject = "clickListener")
    public void onButtonClick(ClickEvent<JmixButton> event) { // <4>
        // ...
    }

    @Install(to = "collectionDl", target = Target.DATA_LOADER)
    public List<Customer> collectionDlLoadDelegate(LoadContext<Customer> loadContext) { // <5>
        return loadCustomers(loadContext);
    }

    @Supply(to = "dataGrid.name", subject = "renderer")
    public Renderer<Customer> dataGridNameRenderer() { // <6>
        return createRenderer();
    }
    // end::autowire[]

    // tag::host[]
    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostInit(View.InitEvent event) {
        // ...
    }

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostBeforeShow(View.BeforeShowEvent event) {
        // ...
    }

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostReady(View.ReadyEvent event) {
        // ...
    }
    // end::host[]

    private List<Customer> loadCustomers(LoadContext<Customer> loadContext) {
        return null;
    }

    private Renderer<Customer> createRenderer() {
        return null;
    }
}
