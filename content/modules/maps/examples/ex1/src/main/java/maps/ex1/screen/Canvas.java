package maps.ex1.screen;

import io.jmix.mapsui.component.*;
import io.jmix.mapsui.component.layer.style.GeometryStyles;
import io.jmix.mapsui.component.layer.style.PolygonStyle;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import maps.ex1.entity.Order;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

@UiController("mapst_Canvas")
@UiDescriptor("canvas.xml")
public class Canvas extends Screen {

    @Inject
    private Notifications notifications;

    // tag::canvas-methods[]
    // tag::map[]
    @Autowired
    private GeoMap map;

    // end::map[]
    // tag::table[]
    @Autowired
    private GroupTable<Order> ordersTable;
    // end::table[]

    @Autowired
    private GeometryStyles geometryStyles;

    protected void drawGeometry() {
        Order order = ordersTable.getSingleSelected();
        CanvasLayer canvasLayer = map.getCanvas();

        CanvasLayer.Point location = canvasLayer.addPoint(order.getLocation());
        location.setStyle(
                geometryStyles.point()
                        .withFontIcon(JmixIcon.HOME)
                        .setIconPathFillColor("#f4d142")
                        .setIconTextFillColor("black")
                        .setIconPathStrokeColor("black"))
                .setEditable(true);
    }
    // end::canvas-methods[]

    @Subscribe("map.canvas")
    private void onCanvasPointClick(CanvasLayer.Point.RightClickEvent clickEvent) {
        notifications.create()
                .withCaption("Right click")
                .show();
    }

    //tag::consumer[]

    @Subscribe("drawPoint")
    private void onDrawPointClick(Button.ClickEvent event) {
        CanvasLayer canvasLayer = map.getCanvas();
        canvasLayer.drawPoint(point -> {
            ordersTable.getSingleSelected().setLocation(point.getGeometry());
        });
    }
    //end::consumer[]

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

    @Subscribe("showPopup")
    public void onShowPopupClick(Button.ClickEvent event) {
        // tag::popup[]
        Order order = ordersTable.getSingleSelected();
        CanvasLayer canvasLayer = map.getCanvas();
        CanvasLayer.Point location = canvasLayer.addPoint(order.getLocation());
        PopupWindowOptions popupWindowOptions = new PopupWindowOptions()
                .setCloseOnClick(true)
                .setMaxWidth(400);
        location.setPopupContent(order.getProduct())
                .setPopupOptions(popupWindowOptions);
        // end::popup[]
    }

    @Subscribe("drawGeometry")
    public void onDrawGeometryClick(Button.ClickEvent event) {
        drawGeometry();
    }

    // tag::popup-window[]
    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        PopupWindow popupWindow = new PopupWindow(map.getCenter(), "Hello");
        map.openPopup(popupWindow);
    }
    // end::popup-window[]

    @Subscribe("showTooltip")
    public void onShowTooltipClick(Button.ClickEvent event) {
        // tag::tooltip[]
        Order order = ordersTable.getSingleSelected();
        CanvasLayer canvasLayer = map.getCanvas();
        CanvasLayer.Point point = canvasLayer.addPoint(order.getLocation());
        TooltipOptions tooltipOptions = new TooltipOptions()
                .setPermanent(true)
                .setOpacity(0.7);
        point.setTooltipContent(order.getProduct())
                .setTooltipOptions(tooltipOptions);
        // end::tooltip[]
    }
}