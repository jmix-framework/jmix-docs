package com.company.onboarding.view.location;

import com.company.onboarding.entity.Location;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.maps.utils.GeometryUtils;
import io.jmix.mapsflowui.component.event.MapClickEvent;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@Route(value = "locations/:id", layout = MainView.class)
@ViewController("Location.detail")
@ViewDescriptor("location-detail-view.xml")
@EditedEntityContainer("locationDc")
// tag::view[]
public class LocationDetailView extends StandardDetailView<Location> {

    protected GeometryFactory geometryFactory = GeometryUtils.getGeometryFactory(); // <1>

    @Subscribe("map")
    public void onMapMapClick(final MapClickEvent event) {
        Point point = geometryFactory.createPoint(event.getCoordinate()); // <2>
        Location location = getEditedEntity(); // <3>
        location.setBuilding(point);
    }
}
// end::view[]