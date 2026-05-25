package search.ex1.screen.order;

import io.jmix.ui.screen.*;
import search.ex1.entity.Order;

@UiController("search_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}