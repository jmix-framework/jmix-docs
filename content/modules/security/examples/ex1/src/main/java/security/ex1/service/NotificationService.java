package security.ex1.service;

import io.jmix.core.AccessManager;
import io.jmix.core.accesscontext.SpecificOperationAccessContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import security.ex1.entity.Customer;

@Component
public class NotificationService {

    // tag::specific-permission[]
    @Autowired
    private AccessManager accessManager;

    public void notifyCustomer(Customer customer) {
        SpecificOperationAccessContext accessContext =
                new SpecificOperationAccessContext("customer.notify");
        accessManager.applyRegisteredConstraints(accessContext);
        if (accessContext.isPermitted()) {
            // do notify
        }
    }
    // end::specific-permission[]
}
