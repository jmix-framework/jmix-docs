package maps.ex1.screen.order;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.GeoMapImpl;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.mapsui.component.leaflet.translators.GeoObjectWrapper;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@UiController("mapst_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit extends StandardEditor<Order> {

}