package com.company.mapssample.view.features;


import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.model.feature.LineStringFeature;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.feature.PointFeature;
import io.jmix.mapsflowui.component.model.feature.PolygonFeature;
import io.jmix.mapsflowui.component.model.layer.VectorLayer;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.feature.Feature;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "features-view", layout = MainView.class)
@ViewController("FeaturesView")
@ViewDescriptor("features-view.xml")
public class FeaturesView extends StandardView {
    // tag::geoMap[]
    @ViewComponent
    private GeoMap geoMap;

    // end::geoMap[]
    // tag::GeometryFactory[]
    private final GeometryFactory geometries = GeometryUtils.getGeometryFactory();
    @Autowired
    private Notifications notifications;

    // end::GeometryFactory[]

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::getSource[]
        VectorLayer vectorLayer = geoMap.getLayer("vector");
        VectorSource vectorSource = vectorLayer.getSource();
        // end::getSource[]
        // tag::addMarker[]
        addMarker(vectorSource);
        // end::addMarker[]
        // tag::addPoint[]
        addPoint(vectorSource);
        // end::addPoint[]
        // tag::addLineString[]
        addLineString(vectorSource);
        // end::addLineString[]
        // tag::addPolygon[]
        addPolygon(vectorSource);
        // end::addPolygon[]
        // tag::onInit[]
    }

    // end::onInit[]
    // tag::addMarker[]
    private void addMarker(VectorSource vectorSource) {
        MarkerFeature feature = new MarkerFeature(GeometryUtils.createPoint(40, 40));
        vectorSource.addFeature(feature);
    }
    // end::addMarker[]

    // tag::addPoint[]
    protected void addPoint(VectorSource source) {
        Point point = GeometryUtils.createPoint(13.046446, 47.797916);
        source.addFeature(new PointFeature(point));
    }
    // end::addPoint[]
    // tag::addLineString[]
    private void addLineString(VectorSource vectorSource) {
        LineString lineString = geometries.createLineString(new Coordinate[]{
                new Coordinate(13, 20),
                new Coordinate(13, 32),
                new Coordinate(25, 17)});

        LineStringFeature feature = new LineStringFeature(lineString);
        vectorSource.addFeature(feature);
    }
    // end::addLineString[]
    // tag::addPolygon[]
    private void addPolygon(VectorSource vectorSource) {
        LinearRing shell = geometries.createLinearRing(new Coordinate[]{
                new Coordinate(1.2457020544488762, 42.476628901048684),
                new Coordinate(-0.054875980233204155, 52.77260344863316),
                new Coordinate(29.858418817454655, 46.105591288830624),
                new Coordinate(1.2457020544488762, 42.476628901048684),
        });

        PolygonFeature feature = new PolygonFeature(geometries.createPolygon(shell));
        vectorSource.addFeature(feature);
    }
    // end::addPolygon[]
}