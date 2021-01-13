package ui.ex1.screen.entity.country;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Country;

@UiController("uiex1_Country.edit")
@UiDescriptor("country-edit.xml")
@EditedEntityContainer("countryDc")
public class CountryEdit extends StandardEditor<Country> {
}