package maps.ex1.screen.territory;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.WMSTileLayer;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Territory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Territory.browsewms2")
@UiDescriptor("territory-browse-wms2.xml")
@LookupComponent("territoriesTable")
public class TerritoryBrowseWms2 extends StandardLookup<Territory> {

    // tag::wms-java[]
    @Autowired
    private GeoMap map;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        WMSTileLayer wmsTileLayer = new WMSTileLayer("wmsLayer");
        wmsTileLayer.setUrl("http://ows.terrestris.de/osm/service?");
        wmsTileLayer.setLayers("OSM-WMS");
        wmsTileLayer.setFormat("image/png");
        map.addLayer(wmsTileLayer);
    }
    // end::wms-java[]
}