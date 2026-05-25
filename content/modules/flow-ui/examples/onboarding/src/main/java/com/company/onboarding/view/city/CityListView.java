package com.company.onboarding.view.city;

import com.company.onboarding.entity.City;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.view.*;

@Route(value = "cities", layout = MainView.class)
@ViewController("City.list")
@ViewDescriptor("city-list-view.xml")
@LookupComponent("citiesDataGrid")
@DialogMode(width = "50em")
public class CityListView extends StandardListView<City> {
    @ViewComponent
    private GenericFilter genericFilter;

    private Boolean isFilterVisible = true;
    // tag::set-value[]
    public void setFilterVisible(Boolean value){
        isFilterVisible = value;
    }
    // end::set-value[]
    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        genericFilter.setVisible(isFilterVisible);
    }
}