package ui.ex1.screen.data;

import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.City;
import ui.ex1.entity.Country;

// tag::controller[]
@UiController("sample_CityBrowse")
@UiDescriptor("city-browse.xml")
@LookupComponent("citiesTable")
public class CityBrowse extends StandardLookup<City> {

    @Autowired
    private CollectionLoader<City> citiesDl;

    private Country country;

    public void setCountry(Country country) {
        this.country = country;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        if (country == null)
            throw new IllegalStateException("Country parameter is null");
        citiesDl.setParameter("country", country);
        citiesDl.load();
    }
}
// end::controller[]