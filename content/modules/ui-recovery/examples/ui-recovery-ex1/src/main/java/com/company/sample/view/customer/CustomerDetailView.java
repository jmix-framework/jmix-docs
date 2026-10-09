package com.company.sample.view.customer;

import com.company.sample.entity.Customer;
import com.company.sample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.formlayout.JmixFormLayout;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.view.*;
import io.jmix.uirecoveryflowui.facet.DraftsFacet;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers/:id", layout = MainView.class)
@ViewController(id = "Customer.detail")
@ViewDescriptor(path = "customer-detail-view.xml")
@EditedEntityContainer("customerDc")
public class CustomerDetailView extends StandardDetailView<Customer> {

    // tag::draft-restored-event[]
    @ViewComponent
    private JmixFormLayout form;

    @Autowired
    private ViewValidation viewValidation;

    @Subscribe("draftsFacet")
    public void onDraftRestored(final DraftsFacet.DraftRestoredEvent event) {
        ValidationErrors errors = viewValidation.validateUiComponents(form);
        if (!errors.isEmpty()) {
            viewValidation.showValidationErrors(errors);
        }
    }
    // end::draft-restored-event[]
}
