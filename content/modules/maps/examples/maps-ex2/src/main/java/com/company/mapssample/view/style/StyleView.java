package com.company.mapssample.view.style;


import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.model.feature.LineStringFeature;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.feature.PointFeature;
import io.jmix.mapsflowui.component.model.feature.PolygonFeature;
import io.jmix.mapsflowui.component.model.layer.VectorLayer;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.style.Fill;
import io.jmix.mapsflowui.kit.component.model.style.Style;
import io.jmix.mapsflowui.kit.component.model.style.image.CircleStyle;
import io.jmix.mapsflowui.kit.component.model.style.image.IconStyle;
import io.jmix.mapsflowui.kit.component.model.style.stroke.Stroke;
import io.jmix.mapsflowui.kit.component.model.style.text.TextStyle;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;

@Route(value = "style-view", layout = MainView.class)
@ViewController("StyleView")
@ViewDescriptor("style-view.xml")
public class StyleView extends StandardView {
    @ViewComponent
    private GeoMap map;

    private final GeometryFactory geometries = GeometryUtils.getGeometryFactory();

    @Subscribe
    public void onInit(final InitEvent event) {
        VectorLayer vectorLayer = map.getLayer("vectorLayer");
        VectorSource vectorSource = vectorLayer.getSource();

        //addMarker(vectorSource);
        //addPoint(vectorSource);
        addPolygon(vectorSource);
        //addLineString(vectorSource);
    }

    private void addMarker(VectorSource vectorSource) {
        // tag::marker[]
        MarkerFeature feature = new MarkerFeature(GeometryUtils.createPoint(20, 20));
        feature.removeAllStyles();
        feature.addStyles(new Style()
                        .withImage(new IconStyle()
                                .withSrc("icons/icon.png")
                                .withScale(0.05)));
        vectorSource.addFeature(feature);
        // end::marker[]
    }

    private void addPoint(VectorSource vectorSource) {
// tag::point[]
        PointFeature styleFeature = new PointFeature(GeometryUtils.createPoint(22, 25))
                .addStyles(new Style()
                        .withImage(new CircleStyle()
                                .withRadius(7)
                                .withFill(new Fill("#E7003E"))
                                .withStroke(new Stroke()
                                        .withWidth(2.0)
                                        .withColor("#710067"))));
        vectorSource.addFeature(styleFeature);
// end::point[]
        // tag::point-text[]
        PointFeature textFeature = new PointFeature(GeometryUtils.createPoint(15, 15))
                .addStyles(new Style()
                        .withText(new TextStyle()
                                .withText("Africa")
                                .withFont("20px sans-serif")
                                .withFill(new Fill("#5E4BD8"))
                                .withStroke(new Stroke()
                                        .withWidth(2.)
                                        .withColor("#A368D5"))));
        vectorSource.addFeature(textFeature);
        // end::point-text[]
    }

    private void addPolygon(VectorSource vectorSource) {
        // tag::polygon[]
        LinearRing shell = geometries.createLinearRing(new Coordinate[]{
                new Coordinate(1.2457020544488762, 42.476628901048684),
                new Coordinate(-0.054875980233204155, 52.77260344863316),
                new Coordinate(29.858418817454655, 46.105591288830624),
                new Coordinate(1.2457020544488762, 42.476628901048684),
        });

        PolygonFeature feature = new PolygonFeature(geometries.createPolygon(shell))
                .addStyles(new Style()
                        .withFill(new Fill("rgba(1, 147, 154, 0.2)"))
                        .withStroke(new Stroke()
                                .withWidth(3.)
                                .withColor("#123EAB")));
        vectorSource.addFeature(feature);

        // end::polygon[]
    }

    private void addLineString(VectorSource vectorSource) {
        // tag::lineString[]
        LineString lineString = geometries.createLineString(new Coordinate[]{
                new Coordinate(13, 20),
                new Coordinate(13, 32),
                new Coordinate(25, 17)});

        LineStringFeature feature = new LineStringFeature(lineString)
                .addStyles(new Style()
                                .withStroke(new Stroke()
                                        .withWidth(3.)
                                        .withColor("#F60018")));
        vectorSource.addFeature(feature);
        // end::lineString[]
    }
}