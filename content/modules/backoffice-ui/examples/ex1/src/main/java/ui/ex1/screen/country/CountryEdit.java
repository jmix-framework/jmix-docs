package ui.ex1.screen.country;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Country;

@UiController("sample_Country.edit")
@UiDescriptor("country-edit.xml")
@EditedEntityContainer("countryDc")
public class CountryEdit extends StandardEditor<Country> {
}