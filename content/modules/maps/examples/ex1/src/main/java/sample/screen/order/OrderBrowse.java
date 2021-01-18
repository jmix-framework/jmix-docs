package sample.screen.order;

import io.jmix.ui.screen.*;
import sample.entity.Order;

@UiController("sample_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}