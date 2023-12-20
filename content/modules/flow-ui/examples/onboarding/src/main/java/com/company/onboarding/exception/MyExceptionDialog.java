package com.company.onboarding.exception;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import io.jmix.flowui.exception.ExceptionDialog;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE) // <1>
public class MyExceptionDialog extends ExceptionDialog { // <2>

    public MyExceptionDialog(Throwable throwable) {
        super(throwable);
    }

    @Override
    protected HorizontalLayout createButtonsPanel() { // <3>
        HorizontalLayout buttonsPanel = super.createButtonsPanel();
        Button button = uiComponents.create(Button.class);
        button.setText("Report to admin");
        button.addClickListener(e -> {
            // ...
        });
        buttonsPanel.add(button);
        return buttonsPanel;
    }
}
