package ui.ex1.screen.data;

import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import ui.ex1.entity.Customer;

@UiController("Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowsePreCommitEvent extends StandardLookup<Customer> {

    // tag::prevent-commit[]
    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreCommit(DataContext.PreCommitEvent event) {
        if (checkSomeCondition()) {
            event.preventCommit();
        }
    }
    // end::prevent-commit[]

    private boolean checkSomeCondition() {
        return true;
    }
}