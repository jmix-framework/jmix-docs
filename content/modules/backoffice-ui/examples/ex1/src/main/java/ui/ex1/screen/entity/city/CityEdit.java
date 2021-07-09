package ui.ex1.screen.entity.city;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.City;
import ui.ex1.entity.Country;

@UiController("uiex1_City.edit")
@UiDescriptor("city-edit.xml")
@EditedEntityContainer("cityDc")
public class CityEdit extends StandardEditor<City> {
    @Autowired
    private Notifications notifications;
    @Autowired
    private EntityPicker<Country> countryField;

    /*@Install(to = "countryField", subject = "lookupSelectHandler")
    private void countryFieldLookupSelectHandler(Collection<Country> collection) {
        notifications.create()
                .withCaption("Selected: ")
                .show();
    }*/

/*    @Install(to = "countryField", subject = "lookupSelectHandler")
    private void countryFieldLookupSelectHandler(Collection<Country> collection) {
        Country country = collection.iterator().next();
        countryField.setValue(country);
    }*/
}