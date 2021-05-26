package ui.ex1.screen.entity.city;

import io.jmix.ui.screen.*;
import ui.ex1.entity.City;

@UiController("uiex1_City.edit")
@UiDescriptor("city-edit.xml")
@EditedEntityContainer("cityDc")
public class CityEdit extends StandardEditor<City> {
}