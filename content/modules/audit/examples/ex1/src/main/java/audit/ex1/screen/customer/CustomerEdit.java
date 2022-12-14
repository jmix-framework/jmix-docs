package audit.ex1.screen.customer;

import io.jmix.audit.snapshot.EntitySnapshotManager;
import io.jmix.auditui.screen.snapshot.SnapshotDiffViewer;
import io.jmix.core.EntityStates;
import io.jmix.ui.screen.*;
import audit.ex1.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ex1_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
    // tag::load-fragment[]
    @Autowired
    protected EntityStates entityStates;

    @Autowired
    protected SnapshotDiffViewer snapshotDiff;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        if (!entityStates.isNew(getEditedEntity())) {
            snapshotDiff.loadVersions(getEditedEntity());
        }
    }
    // end::load-fragment[]

    // tag::create-snapshot[]
    @Autowired
    protected EntitySnapshotManager entitySnapshotManager;

    @Subscribe
    protected void onAfterCommitChanges(AfterCommitChangesEvent event) {
        entitySnapshotManager.createSnapshot(getEditedEntity(),
                getEditedEntityContainer().getFetchPlan());
    }
    // end::create-snapshot[]
}