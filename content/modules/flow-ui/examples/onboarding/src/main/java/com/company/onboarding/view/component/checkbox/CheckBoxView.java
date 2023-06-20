package com.company.onboarding.view.component.checkbox;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "CheckBoxView", layout = MainView.class)
@ViewController("CheckBoxView")
@ViewDescriptor("check-box-view.xml")
public class CheckBoxView extends StandardView {
    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]
    // tag::ClickEvent[]
    @Subscribe("checkbox")
    public void onCheckboxClick(final ClickEvent<Checkbox> event) {
        if (Boolean.TRUE.equals(event.getSource().getValue())) {
            notifications.create("set")
                    .show();
        } else {
            notifications.create("not set")
                    .show();
        }
    }
    // end::ClickEvent[]
}