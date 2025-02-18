package com.company.mapssample.view.layers;

import com.company.mapssample.FeatureHelper;
import com.company.mapssample.entity.Region;
import com.company.mapssample.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.model.source.HasFeatureDrag;
import io.jmix.mapsflowui.component.model.source.HasGeoObjectDrag;
import io.jmix.mapsflowui.component.model.source.VectorSource;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "drag-mode-view", layout = MainView.class)
@ViewController(id = "DragModeView")
@ViewDescriptor(path = "drag-mode-view.xml")
public class DragModeView extends StandardView {
    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    @ViewComponent("map.featureLayer.featureSource")
    private VectorSource featureSource;

    @Subscribe
    public void onReady(final ReadyEvent event) {
        featureSource.addFeature(FeatureHelper.createWestVirginia());
    }

    // tag::dragStartEvent[]
    @Subscribe("map.layer.source")
    public void onSourceGeoObjectDragStart(final HasGeoObjectDrag.SourceGeoObjectDragStartEvent<Region> event) {
        notifications.create("SourceGeoObjectDragStartEvent", "Items: "
                        + event.getItems().size())
                .show();
    }
    // end::dragStartEvent[]

    // tag::dragEndEvent[]
    @Subscribe("map.layer.source")
    public void onSourceGeoObjectDragEnd(final HasGeoObjectDrag.SourceGeoObjectDragEndEvent<Region> event) {
        notifications.create("SourceGeoObjectDragEndEvent", "Items: "
                        + event.getItems().size())
                .show();
    }
    // end::dragEndEvent[]

    // tag::featureDragStartEvent[]
    @Subscribe("map.featureLayer.featureSource")
    public void onSourceFeatureDragStart(final HasFeatureDrag.SourceFeatureDragStartEvent event) {
        notifications.create("SourceFeatureDragStartEvent", "Features: "
                        + event.getFeatures().size())
                .show();
    }
    // end::featureDragStartEvent[]

    // tag::featureDragEndEvent[]
    @Subscribe("map.featureLayer.featureSource")
    public void onSourceFeatureDragEnd(final HasFeatureDrag.SourceFeatureDragEndEvent event) {
        notifications.create("SourceFeatureDragEndEvent", "Features: "
                        + event.getFeatures().size())
                .show();
    }
    // end::featureDragEndEvent[]
}