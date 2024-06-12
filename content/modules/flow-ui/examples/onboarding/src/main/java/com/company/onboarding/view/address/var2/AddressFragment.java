package com.company.onboarding.view.address.var2;

import com.company.onboarding.entity.City;
import com.vaadin.flow.component.formlayout.FormLayout;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.model.CollectionContainer;

// tag::fragment-java[]
@FragmentDescriptor("address-fragment.xml")
public class AddressFragment extends Fragment<FormLayout> {

    public void setCitiesContainer(CollectionContainer<City> citiesContainer) { // <1>
        EntityComboBox<City> cityField = getInnerComponent("cityField");
        cityField.setItems(citiesContainer); // <2>
    }

    public void setZipcodePlaceholder(String placeholder) {
        TypedTextField<String> zipcodeField = getInnerComponent("zipcodeField");
        zipcodeField.setPlaceholder(placeholder);
    }
}
// end::fragment-java[]