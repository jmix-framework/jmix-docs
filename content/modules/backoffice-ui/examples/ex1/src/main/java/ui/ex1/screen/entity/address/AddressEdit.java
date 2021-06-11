package ui.ex1.screen.entity.address;

import com.google.common.base.Strings;
import io.jmix.ui.Dialogs;
import io.jmix.ui.app.inputdialog.DialogActions;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.inputdialog.InputParameter;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Address;
import ui.ex1.entity.City;
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
    // tag::inject-addressField[]
    @Autowired
    private TextField<String> addressField;

    // end::inject-addressField[]
    @Autowired
    private EntityPicker<Country> countryField;
    // tag::inject-dialogs[]
    @Autowired
    private Dialogs dialogs;

    // end::inject-dialogs[]

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
    private void countryEntityComboBoxEnterPressHandler(HasEnterPressHandler.EnterPressEvent
                                                                    enterPressEvent) {
        Country country = dataContext.create(Country.class); // <1>
        country.setName(enterPressEvent.getText()); // <2>
        countriesDc.getMutableItems().add(country); // <3>
        countryEntityComboBox.setValue(country);
    }

    // end::new-option-handler[]
    // tag::context-help-icon-click-handler[]
    @Install(to = "addressField", subject = "contextHelpIconClickHandler")
    private void addressFieldContextHelpIconClickHandler(
            HasContextHelp.ContextHelpIconClickEvent event) {
        dialogs.createInputDialog(this)
                .withCaption("Get values")
                .withParameters(
                        InputParameter.entityParameter("city", City.class)
                                .withCaption("City:")
                                .withRequired(true),
                        InputParameter.stringParameter("street")
                                .withCaption("Street:"),
                        InputParameter.stringParameter("building")
                                .withCaption("Building:"),
                        InputParameter.intParameter("zip")
                                .withCaption("Zip:")
                )
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        City city = closeEvent.getValue("city");
                        String street = closeEvent.getValue("street");
                        String building = closeEvent.getValue("building");
                        Integer zip = closeEvent.getValue("zip");
                        addressField.setValue(city + ", " + street + ", " +
                                building + ", " + zip);
                    }
                })
                .show();
    }
    // end::context-help-icon-click-handler[]
}