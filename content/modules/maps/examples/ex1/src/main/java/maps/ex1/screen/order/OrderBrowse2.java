package maps.ex1.screen.order;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.GeoMapImpl;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.mapsui.component.leaflet.translators.GeoObjectWrapper;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@UiController("mapst_Order.browse2")
@UiDescriptor("order-browse2.xml")
@LookupComponent("ordersTable")
public class OrderBrowse2 extends StandardLookup<Order> {

    // tag::image-icon[]
    @Autowired
    private GeometryStyles geometryStyles;
    // tag::table[]
    @Autowired
    private GroupTable<Order> ordersTable;

    //end::table[]
    @Autowired
    private GeoMap map;

    @Install(to = "map.orderLayer", subject = "styleProvider")
    private GeometryStyle mapOrderLayerStyleProvider1(Order order) {
        return geometryStyles.point()
                .withImageIcon("classpath:/maps/ex1/jmix-logo.png")
                .setIconSize(33, 33);
    }
    // end::image-icon[]

    // tag::wrapper1[]
    @Subscribe("map.orderLayer")
    public void onMapOrderLayerGeoObjectSelected(VectorLayer.GeoObjectSelectedEvent<Order> event) {
        ordersTable.setSelected(event.getItem());
        //end::wrapper1[]
        //tag::get-wrapper[]
        VectorLayer<Order> ordersLayer = map.getLayer("orderLayer");
        Map<?, GeoObjectWrapper<Order>> geoObjectWrappersMap =
                ((GeoMapImpl) map).getGeoObjectWrappersMap(ordersLayer);
        //end::get-wrapper[]
        //tag::wrapper2[]
        GeoObjectWrapper<Order> geoObjectWrapper = geoObjectWrappersMap.get(event.getItem().getId());
        if (geoObjectWrapper != null) {
            geoObjectWrapper.openPopup();
        }
    }
// end::wrapper2[]

    @Install(to = "map.orderLayer", subject = "popupContentProvider")
    private String mapOrderLayerPopupContentProvider(Order order) {
        return String.format(
                "<b>Product: </b> %s " +
                        "<p>" +
                        "<b>Amount: </b> %s",
                order.getProduct(),
                order.getAmount());
    }
}