package com.company.onboarding.view.component.button;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "ButtonView", layout = MainView.class)
@ViewController("ButtonView")
@ViewDescriptor("button-view.xml")
public class ButtonView extends StandardView {
    // tag::basics-1[]
    @Subscribe("toolsButton")
    public void onToolsButtonClick(ClickEvent<Button> event) {
        // ...
    }
    // end::basics-1[]

    // tag::clickEvent[]
    @Subscribe("helloButton") // <1>
    public void onHelloButtonClick(ClickEvent<Button> event) {
        Button button = event.getSource(); // <2>
        // ...
    }
    // end::clickEvent[]

    @Subscribe("helloButton")
    public void onHelloButtonAttach(AttachEvent event) {
        
    }

    @Subscribe("helloButton")
    public void onHelloButtonDetach(DetachEvent event) {

    }

    @Subscribe("helloButton")
    public void onHelloButtonBlur(BlurNotifier.BlurEvent<Button> event) {

    }

    @Subscribe("helloButton")
    public void onHelloButtonFocus(FocusNotifier.FocusEvent<Button> event) {

    }

    @ViewComponent
    private JmixButton helloButton;

    @Subscribe
    public void onInit(InitEvent event) {
        helloButton.setText("aaa");
        helloButton.setTitle("aaaaaa");
    }


}