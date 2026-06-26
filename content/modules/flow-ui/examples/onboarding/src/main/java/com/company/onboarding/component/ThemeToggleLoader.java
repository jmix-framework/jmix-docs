package com.company.onboarding.component;

import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;

public class ThemeToggleLoader extends AbstractComponentLoader<ThemeToggle> {

    @Override
    protected ThemeToggle createComponent() {
        return factory.create(ThemeToggle.class);
    }

    @Override
    public void loadComponent() {
        loadBoolean(element, "autofocus", resultComponent::setAutofocus);
        loadString(element, "storageKey", resultComponent::setStorageKey);

        componentLoader().loadText(resultComponent, element);
        componentLoader().loadTitle(resultComponent, element, context);
        componentLoader().loadTooltip(resultComponent, element);
        componentLoader().loadWhiteSpace(resultComponent, element);
        componentLoader().loadEnabled(resultComponent, element);
        componentLoader().loadClassNames(resultComponent, element);
        componentLoader().loadFocusableAttributes(resultComponent, element);
        componentLoader().loadThemeNames(resultComponent, element);
        componentLoader().loadSizeAttributes(resultComponent, element);
        componentLoader().loadAriaLabel(resultComponent, element);
    }
}
