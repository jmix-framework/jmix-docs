package maps.ex1.screen.order;

import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Order.browse3")
@UiDescriptor("order-browse3.xml")
@LookupComponent("ordersTable")
public class OrderBrowse3 extends StandardLookup<Order> {

    // tag::div-icon[]
    @Autowired
    private GeometryStyles geometryStyles;

    private String divElement = "<img src='https://cdn3.iconfinder.com/data/icons/business-avatar-1/512/3_avatar-128.png' width=60 height = 60/>\n" +
            "<font color = 'green' face = 'arial' size = 2> <i>Salesperson</i></font>";

    @Install(to = "map.orderLayer", subject = "styleProvider")
    private GeometryStyle mapOrderLayerStyleProvider(Order order) {
        return geometryStyles.point()
                .withDivIcon(divElement)
                .setStyles("my-div-style")
                .setPopupAnchor(30, 0);
    }
    // end::div-icon[]
}