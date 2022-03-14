import com.vaadin.ui.Label;
import io.jmix.core.SaveContext;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.sample.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@UiController("Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {

    private static final Logger log = LoggerFactory.getLogger(CustomerBrowse.class);

    @Autowired
    private DataManager dataManager;

    private Label indicatorLabel;

    private User user;

    // tag::commit-delegate[]
    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> commitDelegate(SaveContext saveContext) {
        log.debug("Saving: " + saveContext.getEntitiesToSave());
        return dataManager.save(saveContext);
    }
    // end::commit-delegate[]

    // tag::prevent-commit[]
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreCommit(DataContext.PreCommitEvent event) {
        if (checkSomeCondition()) {
            event.preventCommit();
        }
    }
    // end::prevent-commit[]

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