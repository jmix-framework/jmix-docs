package ui.ex1.screen.data;

import io.jmix.core.LoadContext;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

import java.util.List;

@UiController("uiex1_CustomerData2.browse")
@UiDescriptor("customer-browse-data2.xml")
@LookupComponent("customersTable")
public class CustomerBrowseData2 extends StandardLookup<Customer> {

    // tag::delegate[]
    @Autowired
    private CustomerService customerService;

    @Install(to = "customersDl", target = Target.DATA_LOADER) // <1>
    protected List<Customer> customersDlLoadDelegate(LoadContext<Customer> loadContext) { // <2>
        LoadContext.Query query = loadContext.getQuery();
        return customerService.loadCustomers( // <3>
                query.getCondition(),
                query.getSort(),
                query.getFirstResult(),
                query.getMaxResults()
        );
    }
    // end::delegate[]

    // tag::pre-load[]
    @Subscribe(id = "customersDl", target = Target.DATA_LOADER)
    public void onCustomersDlPreLoad(CollectionLoader.PreLoadEvent<Customer> event) {
        // some actions before loading
    }
    // end::pre-load[]

    // tag::post-load[]
    @Subscribe(id = "customersDl", target = Target.DATA_LOADER)
    public void onCustomersDlPostLoad(CollectionLoader.PostLoadEvent<Customer> event) {
        // some actions after loading
    }
    // end::post-load[]
}