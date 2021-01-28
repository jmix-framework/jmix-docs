package maps.ex1.screen.order;

import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;

@UiController("mapst_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
public class OrderBrowse extends StandardLookup<Order> {

    // tag::tooltip[]
    @Install(to = "map.orderLayer", subject = "tooltipContentProvider")
    private String mapOrderLayerTooltipContentProvider(Order order) {
        return order.getProduct();
    }
    // end::tooltip[]
}