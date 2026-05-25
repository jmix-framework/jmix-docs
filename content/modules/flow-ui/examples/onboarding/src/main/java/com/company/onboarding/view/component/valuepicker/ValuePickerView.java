package com.company.onboarding.view.component.valuepicker;


import com.company.onboarding.view.main.MainView;

import com.google.common.base.Strings;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Actions;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.valuepicker.ValueClearAction;
import io.jmix.flowui.component.valuepicker.JmixValuePicker;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.valuepicker.CustomValueSetEvent;
import io.jmix.flowui.view.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "ValuePickerView", layout = MainView.class)
@ViewController("ValuePickerView")
@ViewDescriptor("value-picker-view.xml")
public class ValuePickerView extends StandardView {
    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]
    // tag::vPicker[]
    @ViewComponent
    private JmixValuePicker<String> vPicker;

    // end::vPicker[]
    // tag::loginValuePicker[]
    @ViewComponent
    private JmixValuePicker<String> loginValuePicker;

    // end::loginValuePicker[]
    // tag::actions[]
    @Autowired
    private Actions actions;

    // end::actions[]
    // tag::custom-action-handler[]
    @ViewComponent
    private JmixValuePicker<String> generateLoginPicker;

    // end::custom-action-handler[]
    // tag::CustomValueSetEvent[]
    @Subscribe("vPicker")
    public void onVPickerCustomValueSet(
            CustomValueSetEvent<JmixValuePicker<String>, Object> event) {
        String text = event.getText(); // <1>
        notifications.create("Entered value: " + text)
                .show();
        if (!Strings.isNullOrEmpty(text))
            vPicker.setValue(text); // <2>
    }
    // end::CustomValueSetEvent[]
    // tag::formatter[]
    @Install(to = "vPicker", subject = "formatter")
    private String vPickerFormatter(String value) {
        return value != null ? "Code: " + value : null;
    }
    // end::formatter[]
    // tag::onInit[]
    @Subscribe
    public void onInit(InitEvent event) {
        // end::onInit[]
        // tag::addPredefinedAction[]
        loginValuePicker.addAction(actions.create(ValueClearAction.ID));
        // end::addPredefinedAction[]
        // tag::onInit[]
    }
    // end::onInit[]
    // tag::custom-action-handler[]
    @Subscribe("generateLoginPicker.generate")
    public void onGenerateLoginPickerGenerate(ActionPerformedEvent event) {
        generateLoginPicker.setValue(RandomStringUtils.randomAlphabetic(5, 10));
    }
    // end::custom-action-handler[]
}