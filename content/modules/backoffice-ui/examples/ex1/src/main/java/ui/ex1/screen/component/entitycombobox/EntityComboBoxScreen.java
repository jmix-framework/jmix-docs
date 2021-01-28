package ui.ex1.screen.component.entitycombobox;

import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Country;

@UiController("entityComboBox-screen")
@UiDescriptor("entitycombobox-screen.xml")
public class EntityComboBoxScreen extends Screen {
    @Autowired
    private CollectionContainer<Country> countriesDc;

    @Install(to = "countryField", subject = "newOptionHandler")
    private void countryFieldNewOptionHandler(String string) {
        Country country = dataContext.create(Country.class);
        country.setName(string);
        countriesDc.getMutableItems().add(country);
        countryField.setValue(country);
    }

    @Autowired
    private EntityComboBox<Country> countryField;
    @Autowired
    private DataContext dataContext;
    // tag::load-data[]
    @Autowired
    private CollectionLoader<Country> countriesDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        countriesDl.load(); //<1>
    }
    // end::load-data[]

}