package com.company.onboarding.view.component.checkboxgroup;


import com.company.onboarding.entity.Hobby;
import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.checkboxgroup.JmixCheckboxGroup;
import io.jmix.flowui.kit.component.ComponentUtils;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Route(value = "CheckboxgroupView", layout = MainView.class)
@ViewController("CheckboxgroupView")
@ViewDescriptor("checkboxgroup-view.xml")
public class CheckboxgroupView extends StandardView {
    @ViewComponent
    private JmixCheckboxGroup<Hobby> checkboxGroup;
    @Autowired
    private Notifications notifications;
    // tag::checkboxGroupInt[]
    @ViewComponent
    private JmixCheckboxGroup<Integer> checkboxGroupInt;

    // end::checkboxGroupInt[]
    // tag::ratingCheckboxGroup[]
    @ViewComponent
    private JmixCheckboxGroup<Integer> ratingCheckboxGroup;

    // end::ratingCheckboxGroup[]

    @Subscribe("getBtn")
    public void onGetBtnClick(final ClickEvent<JmixButton> event) {
        notifications.create("Values: " + checkboxGroup.getTypedValue()).show();
    }

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::setItems[]
        checkboxGroupInt.setItems(new ArrayList<>(Arrays.asList(1,2,3,4,5)));
        // end::setItems[]
        // tag::setItemsMap[]
        Map<Integer,String> map = new LinkedHashMap<>();
        map.put(2,"Poor");
        map.put(3,"Average");
        map.put(4,"Good");
        map.put(5,"Excellent");
        ComponentUtils.setItemsMap(ratingCheckboxGroup, map);
        // end::setItemsMap[]
        // tag::onInit[]
    }
    // end::onInit[]
}