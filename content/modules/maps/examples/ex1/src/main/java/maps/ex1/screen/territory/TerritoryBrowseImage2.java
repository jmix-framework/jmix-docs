package maps.ex1.screen.territory;

import io.jmix.mapsui.component.layer.GeoImage;
import io.jmix.mapsui.component.layer.ImageLayer;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Territory;

@UiController("mapst_Territory.browseimage2")
@UiDescriptor("territory-browse-image2.xml")
@LookupComponent("territoriesTable")
public class TerritoryBrowseImage2 extends StandardLookup<Territory> {

    // tag::image-java[]
    @Install(to = "map.imageLayer", subject = "geoImageDelegate")
    private GeoImage mapImageLayerGeoImageDelegate(ImageLayer imageLayer) {
        String url = "https://legacy.lib.utexas.edu/maps/historical/newark_nj_1922.jpg";
        return GeoImage.fromUrl(url);
    }
    // end::image-java[]
}