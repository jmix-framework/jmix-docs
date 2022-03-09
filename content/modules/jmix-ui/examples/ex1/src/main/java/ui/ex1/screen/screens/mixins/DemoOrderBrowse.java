package ui.ex1.screen.screens.mixins;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Order;
import ui.ex1.screen.screens.mixins.DeclarativeLoaderParameters;

// tag::interface[]
@UiController("demo_Order.browse")
@UiDescriptor("demo-order-browse.xml")
@LookupComponent("ordersTable")
public class DemoOrderBrowse extends StandardLookup<Order> implements DeclarativeLoaderParameters {
}
// end::interface[]