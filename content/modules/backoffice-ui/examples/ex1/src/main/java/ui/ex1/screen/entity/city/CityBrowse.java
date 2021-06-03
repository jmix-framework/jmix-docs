package ui.ex1.screen.entity.city;

import io.jmix.ui.screen.*;
import ui.ex1.entity.City;

@UiController("uiex1_City.browse")
@UiDescriptor("city-browse.xml")
@LookupComponent("citiesTable")
public class CityBrowse extends StandardLookup<City> {
}