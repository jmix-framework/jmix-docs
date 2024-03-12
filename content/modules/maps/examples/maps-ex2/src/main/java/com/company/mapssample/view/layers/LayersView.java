package com.company.mapssample.view.layers;


import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.model.feature.MarkerFeature;
import io.jmix.mapsflowui.component.model.source.ImageStaticSource;
import io.jmix.mapsflowui.component.model.source.VectorSource;

import static io.jmix.maps.utils.GeometryUtils.createPoint;

@Route(value = "layers-view", layout = MainView.class)
@ViewController("LayersView")
@ViewDescriptor("layers-view.xml")
public class LayersView extends StandardView {


    // tag::programmaticSource[]
    @ViewComponent("programmaticMap.pImageLayer.programmaticSource")
    private ImageStaticSource programmaticSource;

    // end::programmaticSource[]
    // tag::vectorSource[]
    @ViewComponent("mapwithvector.vectorLayer.vectorSource")
    private VectorSource vectorSource;

    // end::vectorSource[]
    // tag::onInit[]
    @Subscribe
    protected void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::programmaticMap[]
        programmaticSource.setResource(new StreamResource("map.png",
                () -> this.getClass().getResourceAsStream("/META-INF/resources/maps/map.png")));
        // end::programmaticMap[]
        // tag::mapwithvector[]
        vectorSource.addFeature(new MarkerFeature(createPoint(0, 0)));// <1>
        // end::mapwithvector[]
        // tag::onInit[]
    }
    // end::onInit[]
}