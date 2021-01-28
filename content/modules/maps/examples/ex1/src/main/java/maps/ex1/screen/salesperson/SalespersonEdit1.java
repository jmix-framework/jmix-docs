package maps.ex1.screen.salesperson;

import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.component.layer.TileLayer;
import io.jmix.mapsui.component.layer.VectorLayer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Salesperson;
import maps.ex1.entity.Territory;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Salesperson.edit1")
@UiDescriptor("salesperson-edit1.xml")
@EditedEntityContainer("salespersonDc")
public class SalespersonEdit1 extends StandardEditor<Salesperson> {

    // tag::salesperson-java[]
    @Autowired
    private GeoMap map;

    @Autowired
    private InstanceContainer<Territory> territoryDc;

    @Autowired
    private InstanceContainer<Salesperson> salespersonDc;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        map.setHeight("400px");
        map.setWidth("800px");
        map.setCenter(-99.755859D, 39.164141D);
        map.setZoomLevel(4);

        TileLayer tileLayer = new TileLayer("tileLayer");
        tileLayer.setUrl("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
        tileLayer.setAttributionString("&copy; <a href=\"http://www.openstreetmap.org/copyright\"" +
                ">OpenStreetMap</a> &copy; <a href=\"https://carto.com/attributions\">CARTO</a>");
        map.addLayer(tileLayer);

        VectorLayer<Territory> territoryLayer = new VectorLayer<>("territoryLayer", territoryDc);
        map.addLayer(territoryLayer);

        VectorLayer<Salesperson> salespersonLayer = new VectorLayer<>("salespersonLayer", salespersonDc);
        salespersonLayer.setEditable(true);
        map.addLayer(salespersonLayer);
        map.selectLayer(salespersonLayer);
    }
    // end::salesperson-java[]
}