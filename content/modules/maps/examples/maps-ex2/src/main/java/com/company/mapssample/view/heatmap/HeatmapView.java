package com.company.mapssample.view.heatmap;


import com.company.mapssample.entity.Location;
import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.model.feature.PointFeature;
import io.jmix.mapsflowui.component.model.layer.HeatmapLayer;
import io.jmix.mapsflowui.component.model.source.HeatmapDataVectorSource;
import io.jmix.mapsflowui.component.model.source.VectorSource;

@Route(value = "heatmap-view", layout = MainView.class)
@ViewController("HeatmapView")
@ViewDescriptor("heatmap-view.xml")
public class HeatmapView extends StandardView {
    // tag::vectorSource[]
    @ViewComponent("map.heatmap.vectorSource") // <1>
    private VectorSource vectorSource;

    // end::vectorSource[]
    // tag::heatmapDataVectorSource[]
    @ViewComponent("locationMap.heatmapLayer.heatmapDataVectorSource")
    private HeatmapDataVectorSource<Location> heatmapDataVectorSource; // <1>

    // end::heatmapDataVectorSource[]
    // tag::init[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::init[]
        // tag::without-data-binding[]
        PointFeature feature = new PointFeature(GeometryUtils.createPoint(10, 10));
        feature.withProperty("weight", 1); // <2>

        vectorSource.addFeature(feature);
// end::without-data-binding[]
        // tag::setWeightProvider[]
        heatmapDataVectorSource.setWeightProvider(location -> { // <2>
            int weight = location.getWeight();
            if (weight > 50) {
                return 0.7;
            }
            return 0.3;
        });
        // end::setWeightProvider[]
        // tag::init[]
    }
    // end::init[]
}