package maps.ex1.screen.order;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.HeatMapOptions;
import io.jmix.mapsui.component.layer.HeatMapLayer;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.stream.Collectors;

@UiController("mapst_Heatmap")
@UiDescriptor("heatmap.xml")
public class Heatmap extends Screen {

    // tag::ordersdc[]
    @Autowired
    private CollectionContainer<Order> ordersDc;

    // end::ordersdc[]

    @Autowired
    private GeoMap map;

    // tag::heatmap-java[]
    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {

        HeatMapLayer heatMapLayer = new HeatMapLayer("heatmapLayer");
        heatMapLayer.setDataContainer(ordersDc);
        // end::heatmap-java[]

        HeatMapOptions options = new HeatMapOptions()
                .setMinOpacity(0.3)
                .setMaxZoom(7D)
                .setMaximumIntensity(500D);

        heatMapLayer.setOptions(options);
        map.addLayer(heatMapLayer);
    }

    // tag::datadelegate[]
    @Install(to = "map.heatmapLayer", subject = "dataDelegate")
    private Map<Point, Double> setHeatmapDataDelegate(HeatMapLayer layer) {
        return ordersDc.getItems().stream()
                .filter(order -> order.getLocation()!= null)
                .collect(Collectors.toMap(Order::getLocation, this::calculateIntensity));
    }

    private Double calculateIntensity(Order order) {
        //...
        // end::datadelegate[]
        return 0.0;
        // tag::end[]
    }
    // end::end[]
}