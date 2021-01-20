package maps.ex1.screen.territory;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.TileLayer;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Territory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Territory.edit")
@UiDescriptor("territory-edit.xml")
@EditedEntityContainer("territoryDc")
public class TerritoryEdit extends StandardEditor<Territory> {
    // tag::geometry-style1[]
    @Autowired
    private GeometryStyles geometryStyles;

    // end::geometry-style1[]

    // tag::territory-java[]
    // tag::map[]
    @Autowired
    private GeoMap map;

    @Autowired
    private InstanceContainer<Territory> territoryDc;
    // end::map[]
    @Autowired
    private TileLayer tiles;

    // tag::begin[]
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        // end::begin[]
        tiles.setMaxZoom(14);
        // tag::territory-editable[]
        VectorLayer<Territory> territoryLayer = new VectorLayer<>("territory", territoryDc);
        territoryLayer.setEditable(true);
        map.addLayer(territoryLayer);
    }
    // end::territory-editable[]
    // end::territory-java[]

    // tag::geometry-style2[]
    @Install(to = "map.territoryLayer", subject = "styleProvider")
    private GeometryStyle territoryLayerStyleProvider(Territory territory) {
        return geometryStyles.polygon()
                .setFillColor("#08a343")
                .setStrokeColor("#004912")
                .setFillOpacity(0.3)
                .setStrokeWeight(1);
    }
    // end::geometry-style2[]
}