package ui.ex2.screen.city;

import io.jmix.ui.screen.*;
import ui.ex2.entity.City;

@UiController("sample_City.edit")
@UiDescriptor("city-edit.xml")
@EditedEntityContainer("cityDc")
public class CityEdit extends StandardEditor<City> {
}