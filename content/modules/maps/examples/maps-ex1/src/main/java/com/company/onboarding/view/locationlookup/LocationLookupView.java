package com.company.onboarding.view.locationlookup;


import com.company.onboarding.entity.Location;
import com.company.onboarding.entity.LocationType;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.valuepicker.EntityPicker;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.action.BaseAction;
import io.jmix.flowui.view.*;
import io.jmix.mapsflowui.component.GeoMap;
import io.jmix.mapsflowui.component.model.layer.VectorLayer;
import io.jmix.mapsflowui.component.model.source.DataVectorSource;
import io.jmix.mapsflowui.kit.component.model.style.Fill;
import io.jmix.mapsflowui.kit.component.model.style.Style;
import io.jmix.mapsflowui.kit.component.model.style.image.Anchor;
import io.jmix.mapsflowui.kit.component.model.style.image.CircleStyle;
import io.jmix.mapsflowui.kit.component.model.style.image.IconOrigin;
import io.jmix.mapsflowui.kit.component.model.style.image.IconStyle;
import io.jmix.mapsflowui.kit.component.model.style.stroke.Stroke;
import io.jmix.mapsflowui.kit.component.model.style.text.Padding;
import io.jmix.mapsflowui.kit.component.model.style.text.TextStyle;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;

// tag::controller[]
@Route(value = "LocationLookupView", layout = MainView.class)
@ViewController("LocationLookupView")
@ViewDescriptor("location-lookup-view.xml")
@DialogMode(width = "60em", height = "45em")
public class LocationLookupView extends StandardView {
    // end::controller[]
    @ViewComponent
    private GeoMap map;
    // tag::components[]
    @ViewComponent
    private EntityPicker<Location> currentLocationField;

    @ViewComponent
    private BaseAction select;

    // end::components[]
    // tag::selected[]
    private Location selected;
    // end::selected[]

    // tag::location-param[]
    public Location getSelected() {
        return selected;
    }

    public void setSelected(@Nullable Location selected) {
        this.selected = selected;

        currentLocationField.setValue(selected);

        if (selected != null) {
            setMapCenter(selected.getBuilding().getCoordinate());
        }
    }
    // end::location-param[]
    // tag::initGeoMap[]
    private void initBuildingSource(){
        VectorLayer layer = map.getLayer("dataVectorLayer");

        DataVectorSource<Location> source = layer.getSource(); // <1>
        source.setStyleProvider(location -> new Style() // <2>
                .withImage(new IconStyle()
                        .withScale(0.5)
                        .withAnchorOrigin(IconOrigin.BOTTOM_LEFT)
                        .withAnchor(new Anchor(0.49, 0.12))
                        .withSrc(location.getType() == LocationType.OFFICE
                                ? "icons/office-marker.png"
                                : "icons/coworking-marker.png"))
                .withText(new TextStyle()
                        .withBackgroundFill(new Fill("rgba(255, 255, 255, 0.6)"))
                        .withPadding(new Padding(5, 5, 5, 5))
                        .withOffsetY(15)
                        .withFont("bold 15px sans-serif")
                        .withText(location.getCity())));
        source.addGeoObjectClickListener(clickEvent -> {
            Location location = clickEvent.getItem();

            setMapCenter(location.getBuilding().getCoordinate());

            onLocationChanged(location);
        });
    }
    // end::initGeoMap[]
    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::initBuildingSource[]
        initBuildingSource();
        // end::initBuildingSource[]
        // tag::inits[]
        initBuildingAreaSource();
        initBuildingEntranceSource();
        initPathToBuildingSource();
        // end::inits[]
        // tag::onInit[]
    }
    // end::onInit[]
    // tag::setMapCenter[]
    private void setMapCenter(Coordinate center) {
        map.getMapView().setCenter(center);
        map.getMapView().setZoom(20);
    }
    // end::setMapCenter[]
    // tag::onLocationChanged[]
    private void onLocationChanged(Location newLocation) {
        if (newLocation != null)
            if (!Objects.equals(newLocation, selected)) {
                selected = newLocation;
                select.setEnabled(true); // <1>

                setMapCenter(newLocation.getBuilding().getCoordinate());

                currentLocationField.setValue(newLocation); // <2>
            }
    }
    // end::onLocationChanged[]
    // tag::onSelect[]
    @Subscribe("select")
    public void onSelect(final ActionPerformedEvent event) {
        close(StandardOutcome.SELECT); // <1>
    }
    // end::onSelect[]

// tag::initBuildingAreaSource[]
    private void initBuildingAreaSource() {
        VectorLayer layer = map.getLayer("vectorLayerArea");

        DataVectorSource<Location> source = layer.getSource();
        source.setStyleProvider(location -> { // <1>
            String fillColor = location.getType() == LocationType.COWORKING
                    ? "rgba(52, 216, 0, 0.2)"
                    : "rgba(1, 147, 154, 0.2)";
            String strokeColor = location.getType() == LocationType.COWORKING
                    ? "#228D00"
                    : "#123EAB";
            return new Style()
                    .withFill(new Fill(fillColor))
                    .withStroke(new Stroke()
                            .withWidth(2d)
                            .withColor(strokeColor));
        });
    }
    // end::initBuildingAreaSource[]

    // tag::initBuildingEntranceSource[]
    private void initBuildingEntranceSource() {
        VectorLayer layer = map.getLayer("vectorLayerEntrance");

        DataVectorSource<Location> source = layer.getSource();
        source.setStyleProvider((location -> // <1>
                new Style()
                        .withImage(new CircleStyle()
                                .withRadius(4)
                                .withFill(new Fill("#000000"))
                                .withStroke(new Stroke()
                                        .withWidth(2d)
                                        .withColor("#ffffff")))));
    }
    // end::initBuildingEntranceSource[]
    // tag::initPathToBuildingSource[]
    private void initPathToBuildingSource() {
        VectorLayer layer = map.getLayer("vectorLayerPath");

        DataVectorSource<Location> source = layer.getSource();
        source.setStyleProvider(location -> // <1>
                new Style()
                        .withStroke(new Stroke()
                                .withWidth(2d)
                                .withColor("#000000")
                                .withLineDash(List.of(0.2, 8d, 0.8d))));
    }
    // end::initPathToBuildingSource[]
    // tag::controller[]
}
// end::controller[]