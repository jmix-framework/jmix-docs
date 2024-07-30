package com.company.onboarding.component;

// tag::single-element-component[]
import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;

@Tag("input") // <1>
public class ColorPicker extends AbstractSinglePropertyField<ColorPicker, String> { // <2>

    public ColorPicker() {
        super("value", "", false); // <3>

        getElement().setAttribute("type", "color"); // <4>

        setSynchronizedEvent("change"); // <5>
    }

    // tag::api[]
    // tag::value-api[]
    @Synchronize("change") // <1>
    @Override
    public String getValue() {
        return getElement().getProperty("value", "#000000"); // <2>
    }

    @Override
    public void setValue(String value) {
        getElement().setProperty("value", value);
    }
    // end::value-api[]



    // end::api[]
}
// end::single-element-component[]
