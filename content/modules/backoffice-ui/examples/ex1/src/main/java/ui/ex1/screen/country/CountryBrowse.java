package ui.ex1.screen.country;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Country;

@UiController("uiex1_Country.browse")
@UiDescriptor("country-browse.xml")
@LookupComponent("countriesTable")
public class CountryBrowse extends StandardLookup<Country> {
}