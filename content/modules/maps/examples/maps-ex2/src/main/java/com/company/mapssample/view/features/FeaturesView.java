package com.company.mapssample.view.features;


import com.company.mapssample.view.main.MainView;


import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.model.feature.*;
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
    // tag::vSource[]
    @ViewComponent("geoMap.vectorLayer.vSource")
    private VectorSource vSource;

    // end::vSource[]
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
        // tag::addMultiPoint[]
        MultiPoint multiPoint = geometries.createMultiPoint(new Point[]{
                GeometryUtils.createPoint(2.348582286131592, 48.84306719072006),
                GeometryUtils.createPoint(-0.1503049900024013, 51.54906255948154)});
        vSource.addFeature(new MultiPointFeature(multiPoint));
        // end::addMultiPoint[]
        // tag::addMultiLineString[]
        LineString lineString1 = geometries.createLineString(new Coordinate[]{
                new Coordinate(37.59610278959945, 55.75230701125054),
                new Coordinate(24.94409456852425, 60.18627418174103),
                new Coordinate(12.483009200616205, 41.87228189777008)});

        LineString lineString2 = geometries.createLineString(new Coordinate[]{
                new Coordinate(30.799901492251276, 36.89489780945887),
                new Coordinate(-3.71329777467964, 40.4106248584977)});

        MultiLineString multiLineString = geometries.createMultiLineString(
                new LineString[]{lineString1, lineString2});
        vSource.addFeature(new MultiLineStringFeature(multiLineString));
        // end::addMultiLineString[]
        // tag::addMultiPolygon[]
        Polygon polygon1 = geometries.createPolygon(
                geometries.createLinearRing(new Coordinate[]{
                        new Coordinate(25.095057034220535, 31.6734818426374),
                        new Coordinate(25.095057034220535, 21.916591864189783),
                        new Coordinate(36.273764258555126, 21.916591864189783),
                        new Coordinate(31.711026615969566, 30.893621948904325),
                        new Coordinate(25.095057034220535, 31.6734818426374)}));

        Polygon polygon2 = geometries.createPolygon(
                geometries.createLinearRing(new Coordinate[]{
                        new Coordinate(26.836428848759905, 40.89954566030514),
                        new Coordinate(26.836428848759905, 35.71188725771292),
                        new Coordinate(45.08737941910209, 36.265661479563505),
                        new Coordinate(43.49042124419716, 41.4148387201449),
                        new Coordinate(26.836428848759905, 40.89954566030514)}));

        MultiPolygon multiPolygon = geometries.createMultiPolygon(new Polygon[]
                {polygon1, polygon2});
        vSource.addFeature(new MultiPolygonFeature(multiPolygon));
        // end::addMultiPolygon[]
        // tag::addMultiMarker[]
        MultiPoint multiMarkerPoint = geometries.createMultiPoint(new Point[]{
                GeometryUtils.createPoint(25.235641166883457, 39.917862345662456),
                GeometryUtils.createPoint(25.36806516981515, 37.08014053731078)});
        vSource.addFeature(new MultiMarkerFeature(multiMarkerPoint));
        // end::addMultiMarker[]
        // tag::onInit[]
    }
    // end::onInit[]
}