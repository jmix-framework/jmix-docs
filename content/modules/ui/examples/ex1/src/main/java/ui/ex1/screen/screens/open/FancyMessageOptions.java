package ui.ex1.screen.screens.open;

import io.jmix.ui.screen.ScreenOptions;

// tag::fancy-options[]
public class FancyMessageOptions implements ScreenOptions {

    private String message;

    public FancyMessageOptions(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
// end::fancy-options[]
