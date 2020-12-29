package sample.screen.country;

import io.jmix.ui.screen.*;
import sample.entity.Country;

@UiController("sample_Country.browse")
@UiDescriptor("country-browse.xml")
@LookupComponent("countriesTable")
public class CountryBrowse extends StandardLookup<Country> {
}