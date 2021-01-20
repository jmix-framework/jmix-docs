package maps.ex1.screen;

import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsui.component.*;
import io.jmix.mapsui.component.layer.style.FontPointIcon;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.mapsui.component.layer.style.PointStyle;
import io.jmix.mapsui.component.layer.style.PolygonStyle;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import maps.ex1.entity.Order;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

import javax.inject.Inject;

@UiController("mapst_Canvas")
@UiDescriptor("canvas.xml")
public class Canvas extends Screen {

    @Inject
    private Notifications notifications;

    @Autowired
    private CollectionContainer<Order> ordersDc;

    @Autowired
    private GeometryStyles geometryStyles;


    // tag::canvas-methods[]

    // tag::map[]
    @Autowired
    private GeoMap map;

    // end::map[]
    // tag::table[]
    @Autowired
    private GroupTable<Order> ordersTable;

    // end::table[]
    protected void drawGeometry() {
        Order order = ordersTable.getSingleSelected();
        CanvasLayer canvasLayer = map.getCanvas();
        PopupWindow popupWindow = new PopupWindow(ordersTable.getSingleSelected().getLocation(), "hello");
        map.openPopup(popupWindow);

        CanvasLayer.Point location = canvasLayer.addPoint(order.getLocation());
        location.setStyle(
                geometryStyles.point()
                        .withFontIcon(JmixIcon.HOME)
                        .setIconPathFillColor("#f4d142")
                        .setIconTextFillColor("black")
                        .setIconPathStrokeColor("black"))
                .setPopupContent(order.getDate().toString())
                .setEditable(true);
    }
    // end::canvas-methods[]

    @Subscribe("map.canvas")
    private void onCanvasPointClick(CanvasLayer.Point.RightClickEvent clickEvent) {
        notifications.create()
                .withCaption("Right click")
                .show();
    }

    @Subscribe("drawPoint")
    private void onDrawPointClick(Button.ClickEvent event) {
        //tag::consumer[]
        CanvasLayer canvasLayer = map.getCanvas();
        canvasLayer.drawPoint(point -> {
            ordersTable.getSingleSelected().setLocation(point.getGeometry());
        });
        //end::consumer[]
    }

    @Subscribe("drawPolyline")
    private void onDrawPolylineClick(Button.ClickEvent event) {
        CanvasLayer canvasLayer = map.getCanvas();
        canvasLayer.drawPolyline(polyline ->
                notifications.create()
                        .withCaption("Drawn polyline")
                        .show());
    }

    @Subscribe("drawPolygon")
    private void onDrawPolygonClick(Button.ClickEvent event) {
        CanvasLayer canvasLayer = map.getCanvas();
        PolygonStyle style = new PolygonStyle()
                .setFillColor("#08a343")
                .setStrokeColor("#004912");
        canvasLayer.drawPolygon(polygon -> polygon.setStyle(style));
    }

    // tag::show-order[]
    @Subscribe("showOrder")
    public void onShowOrderClick(Button.ClickEvent event) {
        CanvasLayer canvasLayer = map.getCanvas();
        Point point = ordersTable.getSingleSelected().getLocation();
        canvasLayer.addPoint(point);
    }
    // end::show-order[]

    protected void showPopup() {
        Order order = ordersTable.getSingleSelected();

        PopupWindow popupWindow = new PopupWindow(ordersTable.getSingleSelected().getLocation(), "hello");
        map.openPopup(popupWindow);

        // tag::popup[]
        CanvasLayer canvasLayer = map.getCanvas();
        CanvasLayer.Point location = canvasLayer.addPoint(order.getLocation());
        PopupWindowOptions popupWindowOptions = new PopupWindowOptions()
                .setCloseOnClick(true)
                .setMaxWidth(400);
        location.setPopupContent(order.getProduct())
                .setPopupOptions(popupWindowOptions);
        // end::popup[]

        // tag::tooltip[]
        CanvasLayer.Point point = canvasLayer.addPoint(order.getLocation());
        TooltipOptions tooltipOptions = new TooltipOptions()
                .setPermanent(true)
                .setOpacity(0.7);
        point.setTooltipContent(order.getDate().toString())
                .setTooltipOptions(tooltipOptions);
        // end::tooltip[]
    }

}