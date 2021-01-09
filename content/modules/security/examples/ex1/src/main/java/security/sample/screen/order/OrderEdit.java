package security.sample.screen.order;

import io.jmix.ui.screen.*;
import security.sample.entity.Order;

@UiController("sample_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
}