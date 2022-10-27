package com.company.onboarding.view.dialogssample;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "DialogsSampleView", layout = MainView.class)
@ViewController("DialogsSampleView")
@ViewDescriptor("dialogs-sample-view.xml")
public class DialogsSampleView extends StandardView {
    // tag::inject-dialogs[]
    @Autowired
    private Dialogs dialogs;
    
    // end::inject-dialogs[]

    // tag::messageDialog-1[]
    @Subscribe("helloButton")
    public void onHelloButtonClick(ClickEvent<Button> event) {
        dialogs.createMessageDialog()
                .withHeader("Information")
                .withText("Hello")
                .open();
    }
    // end::messageDialog-1[]

    // tag::optionsDialog-1[]
    @Subscribe("selectOptionButton")
    public void onSelectOptionButtonClick(ClickEvent<Button> event) {
        dialogs.createOptionDialog()
                .withHeader("Please confirm")
                .withText("Do you really want to add a customer?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES)
                                .withHandler(e -> addCustomer()),
                        new DialogAction(DialogAction.Type.NO)
                )
                .open();
    }
    // end::optionsDialog-1[]

    private void addCustomer() {

    }
}