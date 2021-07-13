package ui.ex1.screen.component.grouptable;

import io.jmix.core.Messages;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.component.data.GroupInfo;
import io.jmix.ui.screen.*;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;
import ui.ex1.entity.Level;

@UiController("groupTable-screen")
@UiDescriptor("groupTable-screen.xml")
public class GroupTableScreen extends Screen {
    // tag::inject-groupTableP[]
    @Autowired
    private GroupTable<Customer> groupTableP;

    // end::inject-groupTableP[]

    @Autowired
    private Messages messages;
    // tag::inject-styledTable[]
    @Autowired
    private GroupTable<Customer> styledTable;

    // end::inject-styledTable[]

    @Subscribe("groupTableP.group")
    public void onGroupTablePGroup(Action.ActionPerformedEvent event) {
        // tag::group-by-columns[]
        groupTableP.groupByColumns("level", "city");
        // end::group-by-columns[]

    }

    @Subscribe("groupTableP.ungroup")
    public void onGroupTablePUngroup(Action.ActionPerformedEvent event) {
        // tag::ungroup-by-columns[]
        groupTableP.ungroupByColumns("level");
        // end::ungroup-by-columns[]
    }

    @Subscribe("groupTableP.setCount")
    public void onGroupTablePSetCount(Action.ActionPerformedEvent event) {
        groupTableP.setShowItemsCountForGroup(true);
    }

    @Subscribe("groupTableP.unsetCount")
    public void onGroupTablePUnsetCount(Action.ActionPerformedEvent event) {
        groupTableP.setShowItemsCountForGroup(false);
    }

    // tag::group-cell-value-formatter[]
    @Install(to = "groupTableFormatter", subject = "groupCellValueFormatter")
    private String groupTableFormatterGroupCellValueFormatter(
            GroupTable.GroupCellContext<Customer> context) {
        String key = Customer.class.getSimpleName() +
                "." + context.getGroupInfo().getProperty();
        return messages.getMessage(Customer.class,key) + ": " +
                context.getFormattedValue();
    }
    // end::group-cell-value-formatter[]

    // tag::on-init-start[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::on-init-start[]
        // tag::set-style-provider[]
        styledTable.setStyleProvider(new GroupTable.GroupStyleProvider<Customer>() {
            @Override
            public String getStyleName(Customer entity, @Nullable String property) {
                if (Boolean.TRUE.equals(entity.getEmail() != null)) {
                    return "customer-has-email";
                }
                return null;
            }

            @Nullable
            @Override
            public String getStyleName(GroupInfo info) {
                Object value = info.getPropertyValue(info.getProperty());
                if (value instanceof Level) {
                    Level level = (Level) value;
                    switch (level) {
                        case SILVER:
                            return "level-silver";
                        case GOLD:
                            return "level-gold";
                        case PLATINUM:
                            return "level-platinum";
                        case DIAMOND:
                            return "level-diamond";
                    }
                }
                return null;
            }
        });
        // end::set-style-provider[]
        // tag::on-init-end[]
    }
// end::on-init-end[]

}