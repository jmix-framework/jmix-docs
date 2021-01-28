package maps.ex1.screen.order;

import io.jmix.mapsui.component.layer.HeatMapLayer;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Install;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import maps.ex1.entity.Order;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.stream.Collectors;

@UiController("mapst_Heatmap2")
@UiDescriptor("heatmap2.xml")
public class Heatmap2 extends Screen {

    // tag::datadelegate[]
    @Autowired
    private CollectionContainer<Order> ordersDc;

    @Install(to = "map.heatmapLayer", subject = "dataDelegate")
    private Map<Point, Double> mapHeatmapLayerDataDelegate(HeatMapLayer heatMapLayer) {
        return ordersDc.getItems().stream()
                .filter(order -> order.getLocation() != null)
                .collect(Collectors.toMap(Order::getLocation, this::calculateIntensity));
    }

    private Double calculateIntensity(Order order) {
        //...
        // end::datadelegate[]
        return order.getAmount().doubleValue();
        // tag::end[]
    }
    // end::end[]
}