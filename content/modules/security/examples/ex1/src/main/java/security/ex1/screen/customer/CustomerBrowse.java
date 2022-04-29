package security.ex1.screen.customer;

import io.jmix.core.session.SessionData;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import security.ex1.entity.Customer;
import security.ex1.service.NotificationService;

@UiController("sample_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
// tag::session-data[]
public class CustomerBrowse extends StandardLookup<Customer> {

    @Autowired
    private SessionData sessionData;
    // end::session-data[]

    @Autowired
    private NotificationService notificationService;

    @Subscribe("notifyBtn")
    public void onNotifyBtnClick(Button.ClickEvent event) {
        notificationService.notifyCustomer(null);
    }
}