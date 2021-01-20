package maps.ex1.screen.salesperson;

import io.jmix.mapsui.component.CanvasLayer;
import io.jmix.mapsui.component.GeoMap;
import io.jmix.mapsui.widget.leaflet.shared.Point;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import maps.ex1.entity.Salesperson;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("mapst_Salesperson.browse")
@UiDescriptor("salesperson-browse.xml")
@LookupComponent("salespersonsTable")
public class SalespersonBrowse extends StandardLookup<Salesperson> {

    @Autowired
    private CollectionContainer<Salesperson> salespersonsDc;
    @Autowired
    private GroupTable<Salesperson> salespersonsTable;
    @Autowired
    private GeoMap map;

    @Subscribe("salespersonsTable")
    public void onSalespersonsTableSelection(Table.SelectionEvent<Salesperson> event) {
        CanvasLayer canvasLayer = map.getCanvas();
        Polygon polygon = salespersonsTable.getSingleSelected().getTerritory().getPolygon();
        canvasLayer.addPolygon(polygon);
    }

    // tag::popup-provider[]
    @Install(to = "map.salespersonLayer", subject = "popupContentProvider")
    private String salespersonLayerPopupContentProvider(Salesperson salesperson) {
        return String.format(
                "<b>Name: </b> %s " +
                        "<p>" +
                        "<b>Phone: </b> %s",
                salesperson.getName(),
                salesperson.getPhone());
    }
    // end::popup-provider[]
}