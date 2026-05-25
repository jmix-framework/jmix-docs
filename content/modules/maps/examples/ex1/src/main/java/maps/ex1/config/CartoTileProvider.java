package maps.ex1.config;

import io.jmix.mapsui.component.layer.TileProvider;
import org.springframework.stereotype.Component;

// tag::bean[]
@Component(CartoTileProvider.NAME)
public class CartoTileProvider implements TileProvider {
    public static final String NAME = "sample_CartoTileProvider";

    private static final String URL_PATTERN = "https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png";
    private static final String ATTRIBUTION = "&copy; <a href=\"http://www.openstreetmap.org/copyright\">OpenStreetMap</a> &copy;" +
            " <a href=\"https://carto.com/attributions\">CARTO</a>";

    @Override
    public String getUrlPattern() {
        return URL_PATTERN;
    }

    @Override
    public String getAttributionString() {
        return ATTRIBUTION;
    }
}
// end::bean[]