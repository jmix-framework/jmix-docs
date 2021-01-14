package ui.ex1.screen.entity.address;

import com.google.common.base.Strings;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.ValuePicker;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Address;
import ui.ex1.entity.Country;

import javax.inject.Inject;

@UiController("uiex1_Address.edit")
@UiDescriptor("address-edit.xml")
@EditedEntityContainer("addressDc")
public class AddressEdit extends StandardEditor<Address> {
    // tag::field-value-change[]
    private Country country;

    @Inject
    protected DataContext dataContext;

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
}