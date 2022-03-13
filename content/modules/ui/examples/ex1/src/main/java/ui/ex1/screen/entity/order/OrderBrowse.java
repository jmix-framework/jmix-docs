package ui.ex1.screen.entity.order;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Order;

// tag::lookup-annotations[]
// common annotations
@UiController("sample_Order.browse")
@UiDescriptor("order-browse.xml")
// lookup-specific annotations
@LookupComponent("ordersTable")
@PrimaryLookupScreen(Order.class)
public class OrderBrowse extends StandardLookup<Order> {
    // end::lookup-annotations[]
}