package maps.ex1.screen.territory;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.*;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Territory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Territory.browse")
@UiDescriptor("territory-browse.xml")
@LookupComponent("territoriesTable")
public class TerritoryBrowse extends StandardLookup<Territory> {
    // tag::map1[]
    @Autowired
    private GeoMap map1;

    // end::map1[]

    // tag::map2[]
    @Autowired
    private GeoMap map2;

    // end::map2[]

    // tag::selected[]
    @Autowired
    private GroupTable<Territory> territoriesTable;

    @Subscribe("map.territory")
    public void onMapTerritoryGeoObjectSelected(VectorLayer.GeoObjectSelectedEvent<Territory> event) {
        territoriesTable.setSelected(event.getItem());
    }
    // end::selected[]

    // tag::territory-image-java[]
    @Install(to = "map3.imageLayer", subject = "geoImageDelegate")
    private GeoImage imageDelegate(ImageLayer layer) {
        String url = "https://legacy.lib.utexas.edu/maps/historical/newark_nj_1922.jpg";
        return GeoImage.fromUrl(url);
    }
    // end::territory-image-java[]

    // tag::begin[]
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        // end::begin[]
        // tag::territory-tile-java[]
        TileLayer tileLayer = new TileLayer("tileLayer");
        tileLayer.setUrl("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
        tileLayer.setAttributionString("&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors");
        map1.addLayer(tileLayer);
        // end::territory-tile-java[]

        // tag::territory-wms-java[]
        WMSTileLayer wmsTileLayer = new WMSTileLayer("wmsLayer");
        wmsTileLayer.setUrl("http://ows.terrestris.de/osm/service?");
        wmsTileLayer.setLayers("OSM-WMS");
        wmsTileLayer.setFormat("image/png");
        map2.addLayer(wmsTileLayer);
        // end::territory-wms-java[]
        // tag::end[]
    }
    // end::end[]
}

