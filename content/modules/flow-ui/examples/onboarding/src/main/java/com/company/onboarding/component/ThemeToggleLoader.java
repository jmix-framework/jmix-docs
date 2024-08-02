package com.company.onboarding.component;

import io.jmix.flowui.xml.layout.loader.AbstractComponentLoader;

public class ThemeToggleLoader extends AbstractComponentLoader<ThemeToggle> {

    @Override
    protected ThemeToggle createComponent() {
        return factory.create(ThemeToggle.class);
    }

    @Override
    public void loadComponent() {
        componentLoader().loadTooltip(resultComponent, element);
        componentLoader().loadEnabled(resultComponent, element);
        componentLoader().loadClassNames(resultComponent, element);
        componentLoader().loadThemeNames(resultComponent, element);
        componentLoader().loadSizeAttributes(resultComponent, element);
        componentLoader().loadFocusableAttributes(resultComponent, element);
    }
}
