package ui.ex1.screen.component.label.dataawarelabel;

import io.jmix.core.Metadata;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

@UiController("DataawareLabelScreen")
@UiDescriptor("dataawarelabel-screen.xml")
public class DataawareLabelScreen extends Screen {
    @Autowired
    private InstanceContainer<Customer> customerDc;
    @Autowired
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