package maps.ex1.screen.territory;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.TileLayer;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Territory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Territory.browsetile2")
@UiDescriptor("territory-browse-tile2.xml")
@LookupComponent("territoriesTable")
public class TerritoryBrowseTile2 extends StandardLookup<Territory> {

    // tag::tile-java[]
    @Autowired
    private GeoMap map;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        TileLayer tileLayer = new TileLayer("tileLayer");
        tileLayer.setUrl("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
        tileLayer.setAttributionString("&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors");
        map.addLayer(tileLayer);
    }
    // end::tile-java[]
}