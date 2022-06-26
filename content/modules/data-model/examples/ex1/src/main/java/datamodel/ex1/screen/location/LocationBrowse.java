package datamodel.ex1.screen.location;

import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Location;

@UiController("sample_Location.browse")
@UiDescriptor("location-browse.xml")
@LookupComponent("locationsTable")
public class LocationBrowse extends StandardLookup<Location> {
}