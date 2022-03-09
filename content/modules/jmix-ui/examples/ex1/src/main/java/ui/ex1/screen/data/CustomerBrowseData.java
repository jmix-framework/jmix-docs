package ui.ex1.screen.data;

import io.jmix.core.Metadata;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.CollectionChangeType;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@UiController("uiex1_CustomerData.browse")
@UiDescriptor("customer-browse-data.xml")
@LookupComponent("customersTable")
public class CustomerBrowseData extends StandardLookup<Customer> {

    // tag::container[]
    @Autowired
    private CollectionContainer<Customer> customersDc;

    // end::container[]

    // tag::table[]
    @Autowired
    private GroupTable<Customer> customersTable;

    // end::table[]

    @Autowired
    private TextField<String> textFieldName;

    @Autowired
    private Notifications notifications;

    @Autowired
    private Metadata metadata;

    // tag::find-by-name[]
    private Optional<Customer> findByName(String name) {
        return customersDc.getItems().stream()
                .filter(customer -> Objects.equals(customer.getLastName(), name))
                .findFirst();
    }
    // end::find-by-name[]

    // tag::create-customer[]
    private void createCustomer() {
        Customer customer = metadata.create(Customer.class);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customersDc.getMutableItems().add(customer);
    }
    // end::create-customer[]

    // tag::select-first[]
    private void selectFirstRow() {
        customersTable.setSelected(customersDc.getItems().get(0));
    }
    // end::select-first[]

    @Subscribe("byName")
    public void onByNameClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("name is " + findByName(textFieldName.getRawValue()).get().getFirstName())
                .show();
    }

    @Subscribe("createCustomer")
    public void onCreateCustomerClick(Button.ClickEvent event) {
        createCustomer();
    }

    @Subscribe("selectFirstRow")
    public void onSelectFirstRowClick(Button.ClickEvent event) {
        selectFirstRow();
    }

    // tag::collection-change[]
    @Subscribe(id = "customersDc", target = Target.DATA_CONTAINER)
    public void onCustomersDcCollectionChange(CollectionContainer.CollectionChangeEvent<Customer> event) {
        CollectionChangeType changeType = event.getChangeType(); // <1>
        Collection<? extends Customer> changes = event.getChanges(); // <2>
        // ...
    }
    // end::collection-change[]

}