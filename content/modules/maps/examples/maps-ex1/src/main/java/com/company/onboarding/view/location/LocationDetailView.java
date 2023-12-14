package com.company.onboarding.view.location;

import com.company.onboarding.entity.Location;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "locations/:id", layout = MainView.class)
@ViewController("Location.detail")
@ViewDescriptor("location-detail-view.xml")
@EditedEntityContainer("locationDc")
public class LocationDetailView extends StandardDetailView<Location> {
}