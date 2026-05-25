package com.company.onboarding.view.simple1;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;

@Route(value = "SimpleView1", layout = MainView.class)
@ViewController("SimpleView1")
@ViewDescriptor("simple-view-1.xml")
public class SimpleView1 extends StandardView {

    // tag::components[]
    @ViewComponent
    private TypedTextField<String> nameField;

    @ViewComponent
    private Div greetingsLabel;

    @Subscribe("helloButton")
    private void onHelloButtonClick(ClickEvent<Button> event) {
        greetingsLabel.setText("Hello " + nameField.getTypedValue());
    }
    // end::components[]

}