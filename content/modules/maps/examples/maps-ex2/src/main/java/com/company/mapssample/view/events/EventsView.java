package com.company.mapssample.view.events;


import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.event.MapClickEvent;
import io.jmix.mapsflowui.component.event.MapDoubleClickEvent;
import io.jmix.mapsflowui.component.event.MapMoveEndEvent;
import io.jmix.mapsflowui.component.event.MapZoomChangedEvent;
import io.jmix.mapsflowui.component.model.GeoMapView;
import io.jmix.mapsflowui.component.model.feature.LineStringFeature;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.feature.PointFeature;
import io.jmix.mapsflowui.component.model.feature.PolygonFeature;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.feature.Feature;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "events-view", layout = MainView.class)
@ViewController("EventsView")
@ViewDescriptor("events-view.xml")
public class EventsView extends StandardView {
    // tag::geoMap[]
    @ViewComponent
    private GeoMap geoMap;

    // end::geoMap[]
    // tag::geometryFactory[]
    protected GeometryFactory geometryFactory = GeometryUtils.getGeometryFactory();

    // end::geometryFactory[]
    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]
    // tag::vectorSource[]
    @ViewComponent("geoMap.vector.vectorSource")
    private VectorSource vectorSource;

    // end::vectorSource[]
    // tag::MapClickEvent[]
    @Subscribe("geoMap")
    public void onGeoMapMapClick(final MapClickEvent event) {
        Point point = geometryFactory.createPoint(event.getCoordinate());
        notifications.create("ClickEvent", point.getCoordinate().toString())
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }
    // end::MapClickEvent[]
    // tag::MapDoubleClickEvent[]
    @Subscribe("geoMap")
    public void onGeoMapMapDoubleClick(final MapDoubleClickEvent event) {
        GeoMapView view = new GeoMapView();
        view.setCenter(event.getCoordinate());
        view.setZoom(6);
        geoMap.setMapView(view);
    }
    // end::MapDoubleClickEvent[]
    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::addSourceFeatureClickListener[]
        vectorSource.addSourceFeatureClickListener(clickEvent -> {
            Feature feature = clickEvent.getFeature();
            notifications.create("SourceFeatureClickEvent",
                            feature.getGeometry().get().getClass().getSimpleName())
                    .withPosition(Notification.Position.BOTTOM_END)
                    .show();
        });
        // end::addSourceFeatureClickListener[]
        addMarker(vectorSource);
        addPoint(vectorSource);
        addLineString(vectorSource);
        addPolygon(vectorSource);
        // tag::onInit[]
    }
// end::onInit[]

    private void addMarker(VectorSource vectorSource) {
        MarkerFeature feature = new MarkerFeature(GeometryUtils.createPoint(40, 40));
        vectorSource.addFeature(feature);
    }

    protected void addPoint(VectorSource source) {
        Point point = GeometryUtils.createPoint(13.046446, 47.797916);
        source.addFeature(new PointFeature(point));
    }

    private void addLineString(VectorSource vectorSource) {
        LineString lineString = geometryFactory.createLineString(new Coordinate[]{
                new Coordinate(13, 20),
                new Coordinate(13, 32),
                new Coordinate(25, 17)});

        LineStringFeature feature = new LineStringFeature(lineString);
        vectorSource.addFeature(feature);
    }

    private void addPolygon(VectorSource vectorSource) {
        LinearRing shell = geometryFactory.createLinearRing(new Coordinate[]{
                new Coordinate(1.2457020544488762, 42.476628901048684),
                new Coordinate(-0.054875980233204155, 52.77260344863316),
                new Coordinate(29.858418817454655, 46.105591288830624),
                new Coordinate(1.2457020544488762, 42.476628901048684),
        });

        PolygonFeature feature = new PolygonFeature(geometryFactory.createPolygon(shell));
        vectorSource.addFeature(feature);
    }
    // tag::MapMoveEndEvent[]
    @Subscribe("geoMap")
    public void onGeoMapMapMoveEnd(final MapMoveEndEvent event) {
        notifications.create("MapMoveEndEvent",
                        String.format("""
                                    Values:
                                    Center: %s
                                    Zoom: %s
                                    Rotation: %s
                                    """,
                                event.getCenter(),
                                event.getZoom(),
                                event.getRotation()))
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }
    // end::MapMoveEndEvent[]
    // tag::MapZoomChangedEvent[]
    @Subscribe("geoMap")
    public void onGeoMapMapZoomChanged(final MapZoomChangedEvent event) {
        notifications.create("MapZoomChangedEvent", "Zoom: " + event.getZoom())
                .withPosition(Notification.Position.BOTTOM_END)
                .show();
    }
    // end::MapZoomChangedEvent[]
}