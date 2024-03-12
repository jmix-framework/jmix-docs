package com.company.mapssample.view.features;


import com.company.mapssample.view.main.MainView;


import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.model.feature.LineStringFeature;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.feature.PointFeature;
import io.jmix.mapsflowui.component.model.feature.PolygonFeature;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import org.locationtech.jts.geom.*;

@Route(value = "features-view", layout = MainView.class)
@ViewController("FeaturesView")
@ViewDescriptor("features-view.xml")
public class FeaturesView extends StandardView {
    // tag::GeometryFactory[]
    private final GeometryFactory geometries = GeometryUtils.getGeometryFactory();

    // end::GeometryFactory[]
    // tag::vectorSource[]
    @ViewComponent("geoMap.vector.vectorSource")
    private VectorSource vectorSource;

    // end::vectorSource[]

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::addMarker[]
        vectorSource.addFeature(new MarkerFeature
                (GeometryUtils.createPoint(40, 40)));
        // end::addMarker[]
        // tag::addPoint[]
        vectorSource.addFeature(new PointFeature
                (GeometryUtils.createPoint(13.046446, 47.797916)));
        // end::addPoint[]
        // tag::addLineString[]
        vectorSource.addFeature(new LineStringFeature
                (geometries.createLineString(new Coordinate[]{
                new Coordinate(13, 20),
                new Coordinate(13, 32),
                new Coordinate(25, 17)})));
        // end::addLineString[]
        // tag::addPolygon[]
        vectorSource.addFeature(new PolygonFeature
                (geometries.createPolygon
                        (geometries.createLinearRing(new Coordinate[]{
                        new Coordinate(1.2457020544488762, 42.476628901048684),
                        new Coordinate(-0.054875980233204155, 52.77260344863316),
                        new Coordinate(29.858418817454655, 46.105591288830624),
                        new Coordinate(1.2457020544488762, 42.476628901048684),
                }))));
        // end::addPolygon[]
        // tag::onInit[]
    }
    // end::onInit[]
}