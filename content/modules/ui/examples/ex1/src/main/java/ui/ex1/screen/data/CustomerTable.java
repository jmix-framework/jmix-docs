package ui.ex1.screen.data;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Table;
import io.jmix.ui.component.data.table.ContainerTableItems;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

@UiController("uiex1_CustomerData3.browse")
@UiDescriptor("customer-table.xml")
@LookupComponent("customersTable")
public class CustomerTable extends StandardLookup<Customer> {

    @Autowired
    private UiComponents uiComponents;

    // tag::data-components[]
    @Autowired
    private DataComponents dataComponents;

    // end::data-components[]

    private CollectionContainer<Customer> customersDc;

    // tag::loader[]
    private CollectionLoader<Customer> customersDl;

    // end::loader[]

    // tag::create-loader[]
    private void createCustomerLoader(CollectionContainer<Customer> container) {
        customersDl = dataComponents.createCollectionLoader();
        customersDl.setQuery("select e from uiex1_Customer e");
        customersDl.setContainer(container);
        customersDl.setDataContext(getScreenData().getDataContext());
    }
    // end::create-loader[]


    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        customersDc = dataComponents.createCollectionContainer(Customer.class);
        Table<Customer> customersTable = uiComponents.create(Table.of(Customer.class));
        getWindow().add(customersTable);
        getWindow().expand(customersTable);
        customersTable.setItems(new ContainerTableItems<>(customersDc));
        createCustomerLoader(customersDc);
        customersDl.load();
    }
}