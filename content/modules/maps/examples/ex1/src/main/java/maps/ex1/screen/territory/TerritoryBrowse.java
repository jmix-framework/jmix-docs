package maps.ex1.screen.territory;

import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Territory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Territory.browse")
@UiDescriptor("territory-browse.xml")
@LookupComponent("territoriesTable")
public class TerritoryBrowse extends StandardLookup<Territory> {

    //tag::vector-style[]
    @Autowired
    private GeometryStyles geometryStyles;

    @Install(to = "map.territoryLayer", subject = "styleProvider")
    private GeometryStyle mapTerritoryLayerStyleProvider(Territory territory) {
        return geometryStyles.polygon()
                .setFillColor("#08a343")
                .setStrokeColor("#004912")
                .setFillOpacity(0.3)
                .setStrokeWeight(1);
    }
    //end::vector-style[]
}