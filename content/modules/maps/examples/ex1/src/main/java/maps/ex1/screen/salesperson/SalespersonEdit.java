package maps.ex1.screen.salesperson;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.TileLayer;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.mapsui.component.layer.style.GeometryStyle;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Salesperson;
import maps.ex1.entity.Territory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Salesperson.edit")
@UiDescriptor("salesperson-edit.xml")
@EditedEntityContainer("salespersonDc")
public class SalespersonEdit extends StandardEditor<Salesperson> {

    // tag::salesperson-java[]
    @Autowired
    private GeoMap map;

    @Autowired
    private InstanceContainer<Territory> territoryDc;

    @Autowired
    private InstanceContainer<Salesperson> salespersonDc;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        map.setCenter(-99.755859D, 39.164141D);
        map.setZoomLevel(4);

        TileLayer tileLayer = new TileLayer("tiles");
        tileLayer.setUrl("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
        tileLayer.setAttributionString("&copy; <a href=\"http://www.openstreetmap.org/copyright\"" +
                ">OpenStreetMap</a> &copy; <a href=\"https://carto.com/attributions\">CARTO</a>");
        map.addLayer(tileLayer);

        VectorLayer<Territory> territoryLayer = new VectorLayer<>("territoryLayer", territoryDc);
        map.addLayer(territoryLayer);

        VectorLayer<Salesperson> salespersonLayer = new VectorLayer<>("salespersonLayer", salespersonDc);
        salespersonLayer.setEditable(true);
        map.selectLayer(salespersonLayer);
        map.addLayer(salespersonLayer);
    }
    // end::salesperson-java[]

    // tag::div-icon[]
    @Autowired
    private GeometryStyles geometryStyles;

    private String divElement = "<img src='https://cdn3.iconfinder.com/data/icons/business-avatar-1/512/3_avatar-128.png' width=60 height = 60/>\n" +
            "<font color = 'green' face = 'arial' size = 2> <i>NAME</i></font>";

    @Install(to = "map.salespersonLayer", subject = "styleProvider")
    private GeometryStyle salespersonLayerStyleProvider(Salesperson salesperson) {
        return geometryStyles.point()
                .withDivIcon("classpath:/com/haulmont/demo/maps/web/jmix_icon.png")
                .setStyles("my-div-style")
                .setPopupAnchor(30, 0);
    }
    // end::div-icon[]
}