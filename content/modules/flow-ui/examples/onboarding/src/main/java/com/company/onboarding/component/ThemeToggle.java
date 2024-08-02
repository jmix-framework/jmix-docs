package com.company.onboarding.component;

// tag::web-component[]
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.shared.HasTooltip;
import com.vaadin.flow.shared.Registration;

@Tag("theme-toggle") // <1>
@JsModule("./src/component/theme-toggle/theme-toggle.js") // <2>
public class ThemeToggle extends Component implements ClickNotifier<ThemeToggle>,
        Focusable<ThemeToggle>, HasTheme, HasEnabled, HasSize, HasStyle, HasTooltip, HasAriaLabel { // <3>

    public ThemeToggle() {
    }

    public Registration addThemeChangeListener(ComponentEventListener<ThemeToggleThemeChangedEvent> listener) {
        return addListener(ThemeToggleThemeChangedEvent.class, listener);
    }

    @DomEvent("theme-changed") // <4>
    public static class ThemeToggleThemeChangedEvent extends ComponentEvent<ThemeToggle> {

        protected String value;

        public ThemeToggleThemeChangedEvent(ThemeToggle source, boolean fromClient,
                                            @EventData("event.detail.value") String value) { // <5>
            super(source, fromClient);
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
// end::web-component[]