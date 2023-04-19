package com.company.onboarding.view.component.combobox;


import com.company.onboarding.view.main.MainView;


import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.combobox.JmixComboBox;
import io.jmix.flowui.view.*;
import com.vaadin.flow.component.combobox.ComboBoxBase.CustomValueSetEvent;

import java.util.ArrayList;
import java.util.Arrays;

@Route(value = "ComboBoxView", layout = MainView.class)
@ViewController("ComboBoxView")
@ViewDescriptor("combo-box-view.xml")
public class ComboBoxView extends StandardView {
    // tag::durationComboBox[]
    @ViewComponent
    private JmixComboBox<Integer> durationComboBox;

    // end::durationComboBox[]
    // tag::colorComboBox[]
    @ViewComponent
    private JmixComboBox<String> colorComboBox;

    // end::colorComboBox[]

    // tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit[]
        // tag::setItems[]
        durationComboBox.setItems(new ArrayList<>(Arrays.asList(1,2,3,4,5)));
        // end::setItems[]
        // tag::setColorItems[]
        colorComboBox.setItems(new ArrayList<>
                (Arrays.asList("White", "Red", "Blue", "Grey")));
        // end::setColorItems[]
        // tag::onInit[]
    }
    // end::onInit[]
    // tag::CustomValueSetEvent[]
    @Subscribe("colorComboBox")
    public void onColorComboBoxCustomValueSet(CustomValueSetEvent<ComboBox<String>> event) {
        colorComboBox.setValue(event.getDetail());
    }
    // end::CustomValueSetEvent[]
    // tag::itemLabelGenerator[]
    @Install(to = "colorComboBox", subject = "itemLabelGenerator")
    private String colorComboBoxItemLabelGenerator(String item) {
        return item.toUpperCase();
    }
    // end::itemLabelGenerator[]
}