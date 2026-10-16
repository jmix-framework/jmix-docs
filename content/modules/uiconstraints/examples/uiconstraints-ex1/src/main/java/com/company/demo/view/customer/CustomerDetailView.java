package com.company.demo.view.customer;

import com.company.demo.entity.Customer;
import com.company.demo.view.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.Route;
import io.jmix.core.AccessManager;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.uiconstraints.accesscontext.UiComponentPolicyContext;
import io.jmix.uiconstraints.security.UiComponentPolicyApplier;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers/:id", layout = MainView.class)
@ViewController("Customer.detail")
@ViewDescriptor("customer-detail-view.xml")
@EditedEntityContainer("customerDc")
public class CustomerDetailView extends StandardDetailView<Customer> {

    // tag::accessManager[]
    @Autowired
    private AccessManager accessManager;
    // end::accessManager[]

    // tag::policyApplier[]
    @Autowired
    private UiComponentPolicyApplier uiComponentPolicyApplier;
    // end::policyApplier[]

    // tag::check-policies[]
    private boolean isEmailFieldEditable() {
        UiComponentPolicyContext context =
                new UiComponentPolicyContext("Customer.detail", "emailField");
        accessManager.applyRegisteredConstraints(context);
        return context.isVisiblePermitted() && context.isEnabledPermitted();
    }
    // end::check-policies[]

    // tag::runtime-content[]
    private void addRuntimeContent(Component content) {
        getContent().add(content);
        uiComponentPolicyApplier.applyUiComponentPolicies(this, content);
    }
    // end::runtime-content[]
}