package ui.ex1.screen.entity.address;

import com.google.common.base.Strings;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasEnterPressHandler;
import io.jmix.ui.component.ValuePicker;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Address;
import ui.ex1.entity.Country;

@UiController("uiex1_Address.edit")
@UiDescriptor("address-edit.xml")
@EditedEntityContainer("addressDc")
public class AddressEdit extends StandardEditor<Address> {
    // tag::data[]
    @Autowired
    private CollectionContainer<Country> countriesDc;

    @Autowired
    private EntityComboBox<Country> countryEntityComboBox;

    // tag::field-value-change[]
    @Autowired
    private DataContext dataContext;

    // end::data[]
    private Country country;

    @Autowired
    private EntityPicker<Country> countryField;

    @Subscribe("countryField") // <1>
    public void onCountryFieldFieldValueChange(ValuePicker.FieldValueChangeEvent<Country> event) {
        String value = event.getText(); // <2>
        if (!Strings.isNullOrEmpty(value)) {
            country = dataContext.create(Country.class); // <3>
            country.setName(value);
            countryField.setValue(country); // <4>
        }
    }
    // end::field-value-change[]
    // tag::new-option-handler[]
    @Install(to = "countryEntityComboBox", subject = "enterPressHandler")
    private void countryEntityComboBoxEnterPressHandler(HasEnterPressHandler.EnterPressEvent enterPressEvent) {
        Country country = dataContext.create(Country.class); // <1>
        country.setName(enterPressEvent.getText()); // <2>
        countriesDc.getMutableItems().add(country); // <3>
        countryEntityComboBox.setValue(country);
    }
    // end::new-option-handler[]
}