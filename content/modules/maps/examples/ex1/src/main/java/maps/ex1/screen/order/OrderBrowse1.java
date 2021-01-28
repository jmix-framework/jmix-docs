package maps.ex1.screen.order;

import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Order.browse1")
@UiDescriptor("order-browse1.xml")
@LookupComponent("ordersTable")
public class OrderBrowse1 extends StandardLookup<Order> {

    //tag::table[]
    @Autowired
    private GroupTable<Order> ordersTable;

    //end::table[]
    // tag::font-icon[]
    @Autowired
    private GeometryStyles geometryStyles;

    @Install(to = "map.orderLayer", subject = "styleProvider")
    private GeometryStyle orderLayerStyleProvider(Order order) {
        return geometryStyles.point()
                .withFontIcon(JmixIcon.SHOPPING_BASKET)
                .setIconPathFillColor("#0051d3");
    }
    // end::font-icon[]

    // tag::popup-provider[]
    @Install(to = "map.orderLayer", subject = "popupContentProvider")
    private String mapOrderLayerPopupContentProvider(Order order) {
        return String.format(
                "<b>Product: </b> %s " +
                        "<p>" +
                        "<b>Amount: </b> %s",
                order.getProduct(),
                order.getAmount());
    }
    // end::popup-provider[]

    // tag::selected[]
    @Subscribe("map.orderLayer")
    public void onMapOrderLayerGeoObjectSelected(VectorLayer.GeoObjectSelectedEvent<Order> event) {
        ordersTable.setSelected(event.getItem());
    }
    // end::selected[]
}