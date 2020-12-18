package sample.screen.customer;

import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import sample.bean.CustomerService;
import sample.entity.Customer;

@UiController("sample_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
    @Autowired
    private CustomerService customerService;

    @Subscribe("printProps")
    public void onPrintPropsClick(Button.ClickEvent event) {
        customerService.printOrderProperties();
    }
}