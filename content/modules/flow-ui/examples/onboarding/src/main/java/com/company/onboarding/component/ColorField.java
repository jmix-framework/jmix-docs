package com.company.onboarding.component;

// tag::custom-field[]
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.dom.Style;

public class ColorField extends CustomField<String> {

    private final ColorPicker colorPicker;

    public ColorField() {
        colorPicker = new ColorPicker();
        Span valueLabel = new Span(colorPicker.getValue());
        valueLabel.getStyle().setMarginInlineStart("var(--lumo-space-s)");

        colorPicker.addValueChangeListener(event -> {
            valueLabel.setText(event.getValue());
        });

        add(colorPicker, valueLabel);
    }

    @Override
    protected String generateModelValue() {
        return colorPicker.getValue();
    }

    @Override
    protected void setPresentationValue(String presentationValue) {
        colorPicker.setValue(presentationValue);
    }
}
// end::custom-field[]