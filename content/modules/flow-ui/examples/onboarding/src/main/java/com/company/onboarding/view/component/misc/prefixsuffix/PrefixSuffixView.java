package com.company.onboarding.view.component.misc.prefixsuffix;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "prefix-suffix-view", layout = MainView.class)
@ViewController("PrefixSuffixView")
@ViewDescriptor("prefix-suffix-view.xml")
public class PrefixSuffixView extends StandardView {

    // tag::programmatic-suffix[]
    @ViewComponent
    private JmixTabSheet tabSheet;
    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    public void onInit(final InitEvent event) {
        tabSheet.setSuffixComponent(createAddTabButton()); // <1>
    }

    protected Component createAddTabButton() {
        JmixButton button = uiComponents.create(JmixButton.class);
        button.setIcon(VaadinIcon.PLUS.create());
        button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY_INLINE);

        button.addClickListener(event -> {
            int newTabIndex = tabSheet.getOwnComponents().size() + 1;
            String tabLabel = "Tab " + newTabIndex;
            tabSheet.add(tabLabel, new Div());
        });
        return button;
    }
    // end::programmatic-suffix[]
}