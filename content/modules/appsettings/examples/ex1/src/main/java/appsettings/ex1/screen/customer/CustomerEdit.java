package appsettings.ex1.screen.customer;

import appsettings.ex1.entity.CustomerGrade;
import appsettings.ex1.entity.CustomerSettings;
import io.jmix.appsettings.AppSettings;
import io.jmix.ui.screen.*;
import appsettings.ex1.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("appsetex1_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {

    // tag::app-settings[]
    @Autowired
    private AppSettings appSettings;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Customer> event) {
        CustomerSettings customerSettings = appSettings.load(CustomerSettings.class);
        CustomerGrade defaultGrade = customerSettings.getDefaultGrade();

        Customer customer = event.getEntity();
        customer.setGrade(defaultGrade);
    }
    // end::app-settings[]
}