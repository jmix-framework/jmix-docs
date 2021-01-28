package maps.ex1.screen.order;

import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;

@UiController("mapst_Order.edit1")
@UiDescriptor("order-edit1.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit1 extends StandardEditor<Order> {
}