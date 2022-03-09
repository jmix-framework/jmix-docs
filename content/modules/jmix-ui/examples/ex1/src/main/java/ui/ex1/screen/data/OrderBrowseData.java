package ui.ex1.screen.data;

import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import ui.ex1.entity.Order;

@UiController("uiex1_OrderData.browse")
@UiDescriptor("order-browse-data.xml")
@LookupComponent("ordersTable")
public class OrderBrowseData extends StandardLookup<Order> {
}