package ui.ex1.screen.component.label.dataawarelabel;

import io.jmix.core.Metadata;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import ui.ex1.entity.Customer;

import javax.inject.Inject;

@UiController("sample_DataawareLabelScreen")
@UiDescriptor("dataawareLabel-screen.xml")
public class DataawareLabelScreen extends Screen {
    @Inject
    private InstanceContainer<Customer> customerDc;
    @Inject
    private Metadata metadata;

    @Subscribe
    protected void onInit(InitEvent event) {
        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Customer customer = metadata.create(Customer.class);
        customer.setFirstName("John");
        customerDc.setItem(customer);
    }
}