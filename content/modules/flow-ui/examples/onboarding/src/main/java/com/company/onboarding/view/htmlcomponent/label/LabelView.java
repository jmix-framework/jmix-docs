package com.company.onboarding.view.htmlcomponent.label;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "LabelView", layout = MainView.class)
@ViewController("LabelView")
@ViewDescriptor("label-view.xml")
public class LabelView extends StandardView {

    //tag::dynamicLabel[]
    @ViewComponent
    private Label dynamicLabel;

    @Subscribe("button")
    public void onButtonClick(final ClickEvent<JmixButton> event) {
        dynamicLabel.setText("Button clicked " + event.getClickCount() + " times.");
    }
    //end::dynamicLabel[]

}