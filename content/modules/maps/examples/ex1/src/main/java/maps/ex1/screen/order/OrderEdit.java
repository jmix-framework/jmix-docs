package maps.ex1.screen.order;

import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {

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

}