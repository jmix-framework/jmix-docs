package search.ex1.screen.order;

import io.jmix.ui.screen.*;
import search.ex1.entity.Order;

@UiController("search_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {
}