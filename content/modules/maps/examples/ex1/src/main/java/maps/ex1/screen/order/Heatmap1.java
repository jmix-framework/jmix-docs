package maps.ex1.screen.order;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.HeatMapLayer;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Heatmap1")
@UiDescriptor("heatmap1.xml")
public class Heatmap1 extends Screen {

    @Autowired
    private GeoMap map;

    // tag::orders-dc[]
    // tag::heatmap-java[]
    @Autowired
    private CollectionContainer<Order> ordersDc;

    // end::orders-dc[]
    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {

        HeatMapLayer heatMapLayer = new HeatMapLayer("heatmapLayer");
        heatMapLayer.setIntensityProperty("amount");
        heatMapLayer.setDataContainer(ordersDc);
        // end::heatmap-java[]
        map.addLayer(heatMapLayer);
        // tag::end[]
    }
    // end::end[]
}