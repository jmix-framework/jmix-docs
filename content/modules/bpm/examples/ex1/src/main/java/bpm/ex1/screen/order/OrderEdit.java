package bpm.ex1.screen.order;

import io.jmix.ui.screen.*;
import bpm.ex1.entity.Order;

@UiController("smpl_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
}