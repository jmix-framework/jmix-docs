package com.company.onboarding.view.component.tooltip;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.tools.Tool;

@Route(value = "tooltip-view", layout = MainView.class)
@ViewController("TooltipView")
@ViewDescriptor("tooltip-view.xml")
public class TooltipView extends StandardView {

    // tag::programmaticField[]
    @ViewComponent
    private TypedTextField<String> programmaticField;

    // end::programmaticField[]

    // tag::manualTextField[]
    @ViewComponent
    private TypedTextField<String> manualTextField;

    // end::manualTextField[]
    // tag::uiComponents[]
    @Autowired
    private UiComponents uiComponents;

    // end::uiComponents[]

    // tag::onInit[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::onInit[]
        // tag::set-programmatic[]
        programmaticField.setTooltipText("This is a tooltip")
                .setPosition(Tooltip.TooltipPosition.START);
        // end::set-programmatic[]
        // tag::set-position[]
        programmaticField.getTooltip()
                .setPosition(Tooltip.TooltipPosition.START);
        // end::set-position[]
        // tag::manual[]
        JmixButton helperButton = createHelperButton();
        Tooltip tooltip = manualTextField.getTooltip();
        helperButton.addClickListener(e ->
                tooltip.setOpened(!tooltip.isOpened())); // <1>

        manualTextField.setSuffixComponent(helperButton);
        // end::manual[]
        // tag::onInit[]
    }
    // end::onInit[]
    // tag::createHelperButton[]

    protected JmixButton createHelperButton() {
        JmixButton helperButton = uiComponents.create(JmixButton.class);
        helperButton.setIcon(VaadinIcon.QUESTION_CIRCLE.create());
        helperButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_TERTIARY_INLINE);

        return helperButton;
    }
    // end::createHelperButton[]
}