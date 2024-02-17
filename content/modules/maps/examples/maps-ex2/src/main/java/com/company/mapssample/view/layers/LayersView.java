package com.company.mapssample.view.layers;


import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.layer.ImageLayer;
import io.jmix.mapsflowui.component.model.layer.VectorLayer;
import io.jmix.mapsflowui.component.model.source.ImageStaticSource;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static io.jmix.maps.utils.GeometryUtils.createPoint;

@Route(value = "layers-view", layout = MainView.class)
@ViewController("LayersView")
@ViewDescriptor("layers-view.xml")
public class LayersView extends StandardView {
    // tag::programmaticMap[]
    @ViewComponent
    protected GeoMap programmaticMap;

    // end::programmaticMap[]
    // tag::mapwithvector[]
    @ViewComponent
    private GeoMap mapwithvector;

    // end::mapwithvector[]
    // tag::onInit[]
    @Subscribe
    protected void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::programmaticMap[]
        ImageLayer imageLayer = programmaticMap.getLayer("pImageLayer");
        ImageStaticSource staticSource = imageLayer.getSource();
        staticSource.setResource(new StreamResource("map.png", () -> {
            try {
                return new ClassPathResource("META-INF/resources/maps/map.png").getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        // end::programmaticMap[]
        // tag::mapwithvector[]
        VectorLayer vectorLayer = mapwithvector.getLayer("vectorLayer"); // <1>
        VectorSource vectorSource = vectorLayer.getSource(); // <2>
        vectorSource.addFeature(new MarkerFeature(createPoint(0, 0)));// <3>
        // end::mapwithvector[]
        // tag::onInit[]
    }
    // end::onInit[]
}