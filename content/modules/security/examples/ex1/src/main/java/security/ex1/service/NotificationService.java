package security.ex1.service;

import io.jmix.core.AccessManager;
import io.jmix.core.Metadata;
import io.jmix.core.accesscontext.CrudEntityContext;
import io.jmix.core.accesscontext.SpecificOperationAccessContext;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.ui.WindowConfig;
import io.jmix.ui.WindowInfo;
import io.jmix.ui.accesscontext.UiShowScreenContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import security.ex1.entity.Customer;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationService {

    // tag::access-manager[]
    @Autowired
    private AccessManager accessManager;

    // end::access-manager[]

    // tag::metadata[]
    @Autowired
    private Metadata metadata;

    // end::metadata[]

    // tag::window-config[]
    @Autowired
    private WindowConfig windowConfig;

    // end::window-config[]

    // tag::specific-permission[]
    public void notifyCustomer(Customer customer) {
        SpecificOperationAccessContext accessContext =
                new SpecificOperationAccessContext("customer.notify");
        accessManager.applyRegisteredConstraints(accessContext);
        if (accessContext.isPermitted()) {
            // do notify
        }
    }
    // end::specific-permission[]

    // tag::crud-permission[]
    public boolean checkCustomerReadPermitted() {
        MetaClass metaClass = metadata.getClass(Customer.class);
        CrudEntityContext accessContext = new CrudEntityContext(metaClass);
        accessManager.applyRegisteredConstraints(accessContext);
        return accessContext.isReadPermitted();
    }
    // end::crud-permission[]

    // tag::all-permitted-screens[]
    public List<String> getPermittedScreens() {
        return windowConfig.getWindows().stream()
                .map(WindowInfo::getId)
                .filter(screenId -> {
                    UiShowScreenContext accessContext = new UiShowScreenContext(screenId);
                    accessManager.applyRegisteredConstraints(accessContext);
                    return accessContext.isPermitted();
                })
                .collect(Collectors.toList());
    }
    // end::all-permitted-screens[]

}
