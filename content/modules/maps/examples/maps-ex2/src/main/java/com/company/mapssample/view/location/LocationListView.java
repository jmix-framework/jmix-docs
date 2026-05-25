package com.company.mapssample.view.location;

import com.company.mapssample.entity.Location;

import com.company.mapssample.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "locations", layout = MainView.class)
@ViewController("Location.list")
@ViewDescriptor("location-list-view.xml")
@LookupComponent("locationsDataGrid")
@DialogMode(width = "64em")
public class LocationListView extends StandardListView<Location> {
}