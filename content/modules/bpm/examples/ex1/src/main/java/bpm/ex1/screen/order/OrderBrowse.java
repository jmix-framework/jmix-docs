package bpm.ex1.screen.order;

import io.jmix.ui.screen.*;
import bpm.ex1.entity.Order;

@UiController("smpl_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {
}