package ui.ex1.screen.data;

import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Country;

@UiController("sample_ShowCities")
@UiDescriptor("show-cities.xml")
public class ShowCities extends Screen {

    @Autowired
    private EntityComboBox<Country> comboBox;

    // tag::show[]
    @Autowired
    private ScreenBuilders screenBuilders;

    private void showCitiesOfCountry(Country country) {
        CityBrowse cityBrowse = screenBuilders.screen(this)
                .withScreenClass(CityBrowse.class)
                .build();
        cityBrowse.setCountry(country);
        cityBrowse.show();
    }
    // end::show[]

    @Subscribe("showCities")
    public void onShowCitiesClick(Button.ClickEvent event) {
        showCitiesOfCountry(comboBox.getValue());
    }
}