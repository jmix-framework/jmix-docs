package ui.ex1.screen.data;

import com.vaadin.ui.Label;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.entity.User;
import ui.ex1.screen.entity.customer.CustomerBrowse;

import java.util.Set;

@UiController("Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowseDataContextEvents extends StandardLookup<Customer> {

    private static final Logger log = LoggerFactory.getLogger(CustomerBrowse.class);

    @Autowired
    private DataManager dataManager;

    private Label indicatorLabel;

    private User user;

    // tag::commit-delegate[]
    @Autowired
    private SampleService service;

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> commitDelegate(SaveContext saveContext) {
        return service.saveEntities(
                saveContext.getEntitiesToSave(),
                saveContext.getEntitiesToRemove());
    }
    // end::commit-delegate[]

    // tag::pre-commit-event[]
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreCommit(DataContext.PreCommitEvent event) {
        event.getModifiedInstances().add(user);
    }
    // end::pre-commit-event[]

    // tag::post-commit-event[]
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostCommit(DataContext.PostCommitEvent event) {
        log.debug("Committed: " + event.getCommittedInstances());
    }
    // end::post-commit-event[]

    // tag::change-event[]
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onChange(DataContext.ChangeEvent event) {
        log.debug("Changed entity: " + event.getEntity());
        indicatorLabel.setValue("Changed");
    }
    // end::change-event[]

}