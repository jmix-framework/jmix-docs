package com.company.onboarding.view.component.multiselectcombobox;


import com.company.onboarding.entity.Hobby;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.shared.HasClientValidation;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Stream;

@Route(value = "MultiSelectComboBoxView", layout = MainView.class)
@ViewController("MultiSelectComboBoxView")
@ViewDescriptor("multi-select-combo-box-view.xml")
public class MultiSelectComboBoxView extends StandardView {

    // tag::load-hobbies[]
    @Autowired
    protected DataManager dataManager;

    protected Collection<Hobby> hobbies;

    // end::load-hobbies[]

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::load-hobbies[]
        hobbies = dataManager.load(Hobby.class).all().list();
        // end::load-hobbies[]
        // tag::onInit[]
    }

    // end::onInit[]

    // tag::itemsFetchCallback[]
    @Install(to = "hobbiesComboBox", subject = "itemsFetchCallback")
    private Stream<Hobby> hobbiesComboBoxItemsFetchCallback(final Query<Hobby, String> query) {
        String enteredValue = query.getFilter()
                .orElse("");

        return hobbies.stream()
                .filter(hobby -> hobby.getName() != null &&
                        hobby.getName().toLowerCase().contains(enteredValue.toLowerCase()))
                .skip(query.getOffset())
                .limit(query.getLimit());
    }
    // end::itemsFetchCallback[]
}