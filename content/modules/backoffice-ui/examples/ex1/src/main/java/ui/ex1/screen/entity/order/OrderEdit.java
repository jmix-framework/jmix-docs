package ui.ex1.screen.entity.order;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Order;

@UiController("uiex1_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
}