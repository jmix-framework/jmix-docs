package ui.ex1.screen.screens.mixins;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Order;
import ui.ex1.screen.screens.mixins.HasBanner;

// tag::has-banner[]
@UiController("demo_Order.edit")
@UiDescriptor("demo-order-edit.xml")
@EditedEntityContainer("orderDc")
public class DemoOrderEdit extends StandardEditor<Order> implements HasBanner {
    // ...
}
// end::has-banner[]