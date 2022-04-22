package datamodel.ex1.screen.location;

import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Location;

@UiController("sample_Location.edit")
@UiDescriptor("location-edit.xml")
@EditedEntityContainer("locationDc")
public class LocationEdit extends StandardEditor<Location> {
}