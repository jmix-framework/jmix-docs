package ui.ex1.screen.entity.order;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Order;

@UiController("uiex1_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}