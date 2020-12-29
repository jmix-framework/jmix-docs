package sample.screen.country;

import io.jmix.ui.screen.*;
import sample.entity.Country;

@UiController("sample_Country.edit")
@UiDescriptor("country-edit.xml")
@EditedEntityContainer("countryDc")
public class CountryEdit extends StandardEditor<Country> {
}