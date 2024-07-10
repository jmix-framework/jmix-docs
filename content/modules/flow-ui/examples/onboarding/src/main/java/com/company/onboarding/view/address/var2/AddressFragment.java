package com.company.onboarding.view.address.var2;

import com.company.onboarding.entity.City;
import com.vaadin.flow.component.formlayout.FormLayout;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.ViewComponent;

// tag::fragment-java[]
@FragmentDescriptor("address-fragment.xml")
public class AddressFragment extends Fragment<FormLayout> {

    @ViewComponent
    private EntityComboBox<City> cityField;
    @ViewComponent
    private TypedTextField<String> zipcodeField;

    public void setCitiesContainer(CollectionContainer<City> citiesContainer) { // <1>
        cityField.setItems(citiesContainer);
    }

    public void setZipcodePlaceholder(String placeholder) {
        zipcodeField.setPlaceholder(placeholder);
    }
}
// end::fragment-java[]