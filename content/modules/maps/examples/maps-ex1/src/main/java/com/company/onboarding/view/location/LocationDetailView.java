package com.company.onboarding.view.location;

import com.company.onboarding.entity.Location;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.event.MapClickEvent;
import io.jmix.mapsflowui.component.model.source.DataVectorSource;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "locations/:id", layout = MainView.class)
@ViewController("Location.detail")
@ViewDescriptor("location-detail-view.xml")
@EditedEntityContainer("locationDc")
// tag::view[]
public class LocationDetailView extends StandardDetailView<Location> {
    // end::view[]
    // tag::geometryFactory[]
    protected GeometryFactory geometryFactory = GeometryUtils.getGeometryFactory(); // <1>

    // end::geometryFactory[]

    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]
    // tag::dataVectorSource[]
    @ViewComponent("map.vectorLayer.dataVectorSource")
    private DataVectorSource<Location> dataVectorSource;

    // end::dataVectorSource[]
    // tag::MapClickEvent[]
    @Subscribe("map")
    public void onMapMapClick(final MapClickEvent event) {
        Point point = geometryFactory.createPoint(event.getCoordinate()); // <2>
        Location location = getEditedEntity(); // <3>
        location.setBuilding(point);
    }
    // end::MapClickEvent[]
    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::addGeoObjectClickListener[]
        dataVectorSource.addGeoObjectClickListener(clickEvent ->
                notifications.create("GeoObject click", clickEvent.getItem().getCity())
                .withPosition(Notification.Position.BOTTOM_END)
                .show());
        // end::addGeoObjectClickListener[]
        // tag::onInit[]
    }
    // end::onInit[]
    // tag::view[]
}
// end::view[]