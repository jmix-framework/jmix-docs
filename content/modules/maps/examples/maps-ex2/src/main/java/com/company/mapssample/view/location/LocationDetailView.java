package com.company.mapssample.view.location;

import com.company.mapssample.entity.Location;

import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "locations/:id", layout = MainView.class)
@ViewController("Location.detail")
@ViewDescriptor("location-detail-view.xml")
@EditedEntityContainer("locationDc")
public class LocationDetailView extends StandardDetailView<Location> {
}