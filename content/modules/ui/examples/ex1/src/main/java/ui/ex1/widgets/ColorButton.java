package ui.ex1.widgets;

import io.jmix.ui.widget.JmixButton;
import ui.ex1.widgets.client.ColorButtonState;

import java.util.Objects;

// tag::color-button[]
public class ColorButton extends JmixButton {

    @Override
    protected ColorButtonState getState() {
        return (ColorButtonState) super.getState();
    }

    @Override
    protected ColorButtonState getState(boolean markAsDirty) {
        return (ColorButtonState) super.getState(markAsDirty);
    }

    public String getColor() {
        return getState(false).color;
    }

    public void setColor(String color) {
        if (!Objects.equals(getState(false).color, color)) {
            getState().color = color;
        }
    }
}
// end::color-button[]