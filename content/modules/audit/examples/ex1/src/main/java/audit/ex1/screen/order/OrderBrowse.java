package audit.ex1.screen.order;

import io.jmix.core.DataManager;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import audit.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("ex1_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {

    @Autowired
    private GroupTable<Order> ordersTable;

    @Autowired
    private DataManager dataManager;

    @Subscribe("update")
    public void onUpdateClick(Button.ClickEvent event) {
        Order order = ordersTable.getSingleSelected();
        order.setAmount(10);
        dataManager.save(order);
    }
}