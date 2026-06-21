package com.company.onboarding.component;

// tag::web-component[]
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.shared.HasTooltip;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.shared.Registration;
import io.jmix.flowui.kit.component.HasTitle;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Tag("theme-toggle") // <1>
@JsModule("./src/component/theme-toggle/theme-toggle.js") // <2>
public class ThemeToggle extends Component implements ClickNotifier<ThemeToggle>,
        Focusable<ThemeToggle>, HasTheme, HasEnabled, HasSize, HasStyle,
        HasText, HasTooltip, HasTitle, HasAriaLabel { // <3>

    public static final String STORAGE_KEY_PROPERTY = "storageKey";
    public static final String THEME_CHANGED_EVENT = "theme-changed";
    protected Component iconComponent;

    public ThemeToggle() {
        setIcon(createDefaultIcon());
    }

    public ThemeToggle(Component icon) {
        setIcon(icon);
    }

    public ThemeToggle(String text, Component icon) {
        setIcon(icon);
        setText(text);
    }

    protected Icon createDefaultIcon() {
        Icon icon = VaadinIcon.ADJUST.create();
        icon.getElement().getStyle().set("rotate", "180deg");
        return icon;
    }

    @Override
    public void setText(String text) {
        removeAll(getNonTextNodes());
        if (text != null && !text.isEmpty()) {
            getElement().appendChild(Element.createText(text));
        }
        updateThemeAttribute();
    }

    public Component getIcon() {
        return iconComponent;
    }

    public void setIcon(Component icon) {
        if (icon != null && icon.getElement().isTextNode()) {
            throw new IllegalArgumentException("Text node can't be used as an icon.");
        }
        if (iconComponent != null) {
            remove(iconComponent);
        }

        iconComponent = icon;
        if (icon != null) {
            add(icon);
            updateIconSlot();
        }

        updateThemeAttribute();
    }

    protected void updateIconSlot() {
        iconComponent.getElement().setAttribute("slot", "prefix");
    }

    protected void add(Component... components) {
        for (Component component : components) {
            getElement().appendChild(component.getElement());
        }
    }

    protected void remove(Component... components) {
        for (Component component : components) {
            if (getElement().equals(component.getElement().getParent())) {
                component.getElement().removeAttribute("slot");
                getElement().removeChild(component.getElement());
            } else {
                throw new IllegalArgumentException(
                        "The given component (" + component + ") is not a child of this component");
            }
        }
    }

    public boolean isAutofocus() {
        return getElement().getProperty("autofocus", false);
    }

    public void setAutofocus(boolean autofocus) {
        getElement().setProperty("autofocus", autofocus);
    }

    public String getStorageKey() {
        return getElement().getProperty(STORAGE_KEY_PROPERTY);
    }

    public void setStorageKey(String storageKey) {
        getElement().setProperty(STORAGE_KEY_PROPERTY, storageKey);
    }

    protected void removeAll(Element... exclusion) {
        Set<Element> toExclude = Stream.of(exclusion)
                .collect(Collectors.toSet());
        Predicate<Element> filter = toExclude::contains;

        getElement().getChildren()
                .filter(filter.negate())
                .forEach(child -> child.removeAttribute("slot"));

        getElement().removeAllChildren();
        getElement().appendChild(exclusion);
    }

    protected Element[] getNonTextNodes() {
        return getElement().getChildren()
                .filter(element -> !element.isTextNode())
                .toArray(Element[]::new);
    }

    protected void updateThemeAttribute() {
        long childCount = getElement().getChildren()
                .filter(element ->
                        element.isTextNode() || !"vaadin-tooltip".equals(element.getTag()))
                .count();

        if (childCount == 1 && iconComponent != null) {
            getThemeNames().add("icon");
        } else {
            getThemeNames().remove("icon");
        }
    }

    public Registration addThemeChangeListener(ComponentEventListener<ThemeToggleThemeChangedEvent> listener) {
        return addListener(ThemeToggleThemeChangedEvent.class, listener);
    }

    @DomEvent(THEME_CHANGED_EVENT) // <4>
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
