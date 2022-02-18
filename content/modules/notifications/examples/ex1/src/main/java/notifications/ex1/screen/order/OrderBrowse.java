package notifications.ex1.screen.order;

import io.jmix.ui.screen.*;
import notifications.ex1.entity.Order;

@UiController("ntf_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}