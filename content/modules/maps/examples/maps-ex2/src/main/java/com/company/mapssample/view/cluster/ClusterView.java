package com.company.mapssample.view.cluster;


import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.model.feature.PointFeature;
import io.jmix.mapsflowui.component.model.layer.VectorLayer;
import io.jmix.mapsflowui.component.model.source.ClusterSource;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import io.jmix.mapsflowui.kit.component.model.feature.Feature;
import io.jmix.mapsflowui.kit.component.model.style.Fill;
import io.jmix.mapsflowui.kit.component.model.style.PointStyle;
import io.jmix.mapsflowui.kit.component.model.style.Style;
import io.jmix.mapsflowui.kit.component.model.style.image.CircleStyle;
import io.jmix.mapsflowui.kit.component.model.style.text.TextStyle;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Route(value = "cluster-view", layout = MainView.class)
@ViewController("ClusterView")
@ViewDescriptor("cluster-view.xml")
public class ClusterView extends StandardView {

    // tag::cluster[]
    @ViewComponent("locationMap.vectorLr.cluster")
    public ClusterSource cluster;

    // end::cluster[]

    // tag::clusterS[]
    @ViewComponent("styledMap.vectLr.clusterS")
    public ClusterSource clusterS;

    // end::clusterS[]

    // tag::clusterSource[]
    @ViewComponent("textStyleMap.vctLayer.clusterSource")
    public ClusterSource clusterSource;

    // end::clusterSource[]
    // tag::source[]
    @ViewComponent("featuresMap.pointsLayer.pointClusterSource.source")
    public VectorSource source;

    // end::source[]

    // tag::init[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::init[]
        // tag::new-style[]
        cluster.addPointStyles(new Style()
                .withImage(new CircleStyle()
                        .withRadius(15)
                        .withFill(new Fill("#7F70D8"))));
// end::new-style[]
        // tag::remove-add[]
        clusterS.removeAllPointStyles();
        clusterS.addPointStyles(
                new Style()
                        .withImage(new CircleStyle()
                                .withRadius(20)
                                .withFill(new Fill("rgba(163, 104, 213, 0.6)"))),
                new Style()
                        .withImage(new CircleStyle()
                                .withRadius(15)
                                .withFill(new Fill("rgba(144, 64, 213, 1)"))));
        // end::remove-add[]
        // tag::text-style[]
        clusterSource.setPointTextStyle(new Style()
                .withText(new TextStyle()
                        .withFont("13px sans-serif")
                        .withOffsetY(1)
                        .withFill(new Fill("#FAAF22"))));
// end::text-style[]
        // tag::addAllFeatures[]
        source.addAllFeatures(generatePoints());
        // end::addAllFeatures[]
        // tag::init[]
    }
    // end::init[]

    // tag::generatePoints[]
    private List<Feature> generatePoints() {
        List<Feature> features = new ArrayList<>(10000);
        int e = 45;
        for (int i = 0; i < 10000; i++) {
            Point point = GeometryUtils.createPoint(2 * e * Math.random(),
                    2 * e * Math.random() - e);
            features.add(
                    new PointFeature(point)
                            .withProperty("weight",
                                    Double.valueOf(Math.random() * 10).intValue()));
        }
        return Collections.unmodifiableList(features);
    }
    // end::generatePoints[]
}