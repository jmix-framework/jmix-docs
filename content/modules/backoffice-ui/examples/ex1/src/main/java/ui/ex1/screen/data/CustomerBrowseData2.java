package ui.ex1.screen.data;

import io.jmix.core.DataManager;
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

    // tag::data-manager[]
    @Autowired
    private DataManager dataManager;

    // end::data-manager[]

    // tag::delegate[]
    @Install(to = "customersDl", target = Target.DATA_LOADER)// <1>
    protected List<Customer> customersDlLoadDelegate(LoadContext<Customer> loadContext) { // <2>
        return dataManager.loadList(loadContext); // <3>
    }
    // end::delegate[]

    // tag::load[]
    @Subscribe(id = "customersDl", target = Target.DATA_LOADER)
    public void onCustomersDlPreLoad(CollectionLoader.PreLoadEvent<Customer> event) {
        // some actions before loading
    }

    @Subscribe(id = "customersDl", target = Target.DATA_LOADER)
    public void onCustomersDlPostLoad(CollectionLoader.PostLoadEvent<Customer> event) {
        // some actions after loading
    }
    // end::load[]
}