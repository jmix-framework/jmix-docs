package com.company.onboarding.component;

// tag::composite-component[]
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ColorComponent extends Composite<HorizontalLayout> { // <1>

    private final ColorPicker colorPicker;

    public ColorComponent() {
        colorPicker = new ColorPicker();
        Span valueLabel = new Span(colorPicker.getValue());

        colorPicker.addValueChangeListener(event ->
                valueLabel.setText(event.getValue()));

        getContent().add(colorPicker, valueLabel); // <2>
    }

    @Override
    protected HorizontalLayout initContent() { // <3>
        HorizontalLayout content = super.initContent();
        content.setAlignItems(FlexComponent.Alignment.CENTER);

        return content;
    }
    // tag::api[]

    public String getValue() { // <4>
        return colorPicker.getValue();
    }

    public void setValue(String value) {
        colorPicker.setValue(value);
    }
    // end::api[]
}
// end::composite-component[]