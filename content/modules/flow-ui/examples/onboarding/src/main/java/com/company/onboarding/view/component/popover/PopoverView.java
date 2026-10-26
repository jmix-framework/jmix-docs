package com.company.onboarding.view.component.popover;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.popover.Popover;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "popover-view", layout = MainView.class)
@ViewController("PopoverView")
@ViewDescriptor("popover-view.xml")
public class PopoverView extends StandardView {

    // tag::basics[]
    @ViewComponent
    private Popover infoPopover;

    // end::basics[]
    // tag::runtimeTarget[]
    @ViewComponent
    private Popover emailHelpPopover;
    @ViewComponent
    private TypedTextField<String> emailField;
    @Autowired
    private UiComponents uiComponents;

    // end::runtimeTarget[]
    // tag::programmatic[]
    @ViewComponent
    private Popover promoCodePopover;

    // end::programmatic[]
    // tag::modal[]
    @ViewComponent
    private Popover renamePopover;
    @ViewComponent
    private H3 reportTitle;
    @ViewComponent
    private TypedTextField<String> titleField;

    // end::modal[]
    // tag::basics[]
    @Subscribe(id = "closeButton", subject = "clickListener")
    public void onCloseButtonClick(final ClickEvent<JmixButton> event) {
        infoPopover.close();
    }
    // end::basics[]

    // tag::runtimeTarget[]
    @Subscribe
    public void onInit(final InitEvent event) {
        JmixButton helpButton = uiComponents.create(JmixButton.class);
        helpButton.setIcon(VaadinIcon.QUESTION_CIRCLE.create());
        helpButton.addThemeVariants(ButtonVariant.TERTIARY);
        emailField.setSuffixComponent(helpButton);

        emailHelpPopover.setTarget(helpButton);
    }
    // end::runtimeTarget[]

    // tag::programmatic[]
    @Subscribe(id = "promoCodeHelpButton", subject = "clickListener")
    public void onPromoCodeHelpButtonClick(final ClickEvent<JmixButton> event) {
        promoCodePopover.open();
    }
    // end::programmatic[]

    // tag::modal[]
    @Subscribe("renamePopover")
    public void onRenamePopoverOpenedChange(final Popover.OpenedChangeEvent event) {
        if (event.isOpened()) {
            titleField.setValue(reportTitle.getText());
        }
    }

    @Subscribe(id = "saveButton", subject = "clickListener")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        reportTitle.setText(titleField.getValue());
        renamePopover.close();
    }

    @Subscribe(id = "cancelButton", subject = "clickListener")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        renamePopover.close();
    }
    // end::modal[]
}
