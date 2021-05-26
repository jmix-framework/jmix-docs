package ui.ex1.screen.entity.order;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Order;

// tag::edit-annotations[]
// common annotations
@UiController("sample_Order.edit")
@UiDescriptor("order-edit.xml")
// editor-specific annotations
@EditedEntityContainer("orderDc")
@PrimaryEditorScreen(Order.class)
public class OrderEdit extends StandardEditor<Order> {
    // end::edit-annotations[]
}