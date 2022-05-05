package ui.ex2.screen.city;

import io.jmix.ui.component.Filter;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex2.entity.City;

@UiController("sample_City.browse")
@UiDescriptor("city-browse.xml")
@LookupComponent("citiesTable")
public class CityBrowse extends StandardLookup<City> {
    @Autowired
    private Filter filter;

    private Boolean isFilterVisible = true;
    // tag::set-value[]
    public void setFilterVisible(Boolean value){
        isFilterVisible = value;
    }
    // end::set-value[]
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        filter.setVisible(isFilterVisible);
    }
}