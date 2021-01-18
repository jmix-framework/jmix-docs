package security.ex1.screen.order;

import io.jmix.ui.screen.*;
import security.ex1.entity.Order;

@UiController("sample_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}