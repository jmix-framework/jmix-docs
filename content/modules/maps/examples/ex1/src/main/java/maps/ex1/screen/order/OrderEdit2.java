package maps.ex1.screen.order;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.TileLayer;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Order.edit2")
@UiDescriptor("order-edit2.xml")
@EditedEntityContainer("orderDc")
public class OrderEdit2 extends StandardEditor<Order> {

    // tag::vector-controller1[]
    // tag::layers-java[]
    @Autowired
    private GeoMap map;

    @Autowired
    private InstanceContainer<Order> orderDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        // end::vector-controller1[]
        TileLayer tileLayer = new TileLayer("tileLayer");
        tileLayer.setUrl("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
        tileLayer.setAttributionString("&copy; <a href=\"http://www.openstreetmap.org/copyright\"" +
                ">OpenStreetMap</a> &copy; <a href=\"https://carto.com/attributions\">CARTO</a>");
        map.addLayer(tileLayer);
        // tag::vector-controller2[]
        VectorLayer<Order> orderLayer = new VectorLayer<>("orderLayer", orderDc);
        orderLayer.setEditable(true);
        map.addLayer(orderLayer);
        map.selectLayer(orderLayer);
    }
    // end::layers-java[]
    // end::vector-controller2[]
}