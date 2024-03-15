package com.company.mapssample.view.mapbasic;


import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.event.MapSingleClickEvent;
import io.jmix.mapsflowui.component.model.FitOptions;
import io.jmix.mapsflowui.component.model.feature.PointFeature;
import io.jmix.mapsflowui.component.model.layer.TileLayer;
import io.jmix.mapsflowui.component.model.source.OsmSource;
import io.jmix.mapsflowui.component.model.source.XyzSource;
import io.jmix.mapsflowui.kit.component.model.Easing;
import org.locationtech.jts.geom.GeometryFactory;

@Route(value = "map-basic-view", layout = MainView.class)
@ViewController("MapBasicView")
@ViewDescriptor("map-basic-view.xml")
public class MapBasicView extends StandardView {
    // tag::geometryFactory[]
    protected GeometryFactory geometryFactory = GeometryUtils.getGeometryFactory();

    // end::geometryFactory[]
    // tag::geoMap[]
    @ViewComponent
    private GeoMap geoMap;

    // end::geoMap[]
    // tag::mapTile[]
    @ViewComponent("map.tile")
    private TileLayer mapTile;

    // end::mapTile[]
    // tag::osmSource[]
    @ViewComponent("map.tile.osmSource")
    private OsmSource osmSource;

    // end::osmSource[]
    // tag::map[]
    @ViewComponent
    private GeoMap map;

    // end::map[]
    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::geoMap[]
        geoMap.addLayer(new TileLayer()
                .withSource(new OsmSource()
                        .withUrl("https://tile.openstreetmap.org/{z}/{x}/{y}.png")
                        .withOpaque(true)
                        .withMaxZoom(10)));
        // end::geoMap[]
        // tag::mapTile[]
        mapTile.setSource(new XyzSource()
                .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x}"));
        // end::mapTile[]
        // tag::osmSource[]
        osmSource.withUrl("https://tile.openstreetmap.org/{z}/{x}/{y}.png")
                .withMaxZoom(12)
                .withWrapX(false);
        // end::osmSource[]
        // tag::onInit[]
    }
    // end::onInit[]
    // tag::fit[]
    @Subscribe("map")
    public void onMapMapSingleClick(final MapSingleClickEvent event) {
        map.fit(new FitOptions(new PointFeature(
                geometryFactory.createPoint(event.getCoordinate())))
                .withDuration(2000)
                .withEasing(Easing.LINEAR)
                .withMaxZoom(20d));
    }
    // end::fit[]
}