package ui.ex1.screen.component.popupbutton;

import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.PopupButton;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Status;

@UiController("PopupButtonScreen")
@UiDescriptor("popup-button-screen.xml")
public class PopupButtonScreen extends Screen {
    @Autowired
    protected PopupButton customPopupButton;

    @Autowired
    protected TextField<String> textField;

    @Autowired
    protected ComboBox<Status> comboBox;

    // tag::listener[]
    @Autowired
    protected PopupButton popupButton;
    // tag::event[]
    @Autowired
    protected Notifications notifications;

    // end::event[]
    @Subscribe
    protected void onInit(InitEvent event) {
        // end::listener[]
        popupButton.addAction(new BaseAction("saveAsDocAction")
                .withCaption("Save as .doc")
                .withHandler(actionPerformedEvent -> saveAsDoc()));

        popupButton.addAction(new BaseAction("saveAsPdfAction")
                .withCaption("Save as .pdf")
                .withHandler(actionPerformedEvent -> saveAsPdf()));
        // tag::listener[]
        popupButton.addPopupVisibilityListener(popupVisibilityEvent ->
                notifications.create()
                        .withCaption("Popup visibility changed")
                        .show());
    }
    // end::listener[]

    // tag::event[]
    @Subscribe("popupButton")
    public void onPopupButtonPopupVisibility(PopupButton.PopupVisibilityEvent event) {
        notifications.create()
                .withCaption("Popup visibility changed")
                .show();
    }
    // end::event[]


    @Subscribe("popupButton1.popupAction1")
    protected void onPopupButton1PopupAction1ActionPerformed(Action.ActionPerformedEvent event) {
        saveAsDoc();
    }

    @Subscribe("popupButton1.popupAction2")
    protected void onPopupButton1PopupAction2ActionPerformed(Action.ActionPerformedEvent event) {
        saveAsPdf();
    }

    @Subscribe("popupButton2.popupAction1")
    protected void onPopupButton2PopupAction1ActionPerformed(Action.ActionPerformedEvent event) {
        saveAsDoc();
    }

    @Subscribe("popupButton2.popupAction2")
    protected void onPopupButton2PopupAction2ActionPerformed(Action.ActionPerformedEvent event) {
        saveAsPdf();
    }

    public void saveAsDoc() {
        notifications.create()
                .withCaption("Saved as DOC")
                .show();
    }

    public void saveAsPdf() {
        notifications.create()
                .withCaption("Saved as PDF")
                .show();
    }

    @Subscribe("saveAndCloseButton")
    protected void onSaveAndCloseButtonClick(Button.ClickEvent event) {
        customPopupButton.setPopupVisible(false);

        notifications.create()
                .withCaption("Settings saved")
                .show();
    }

    @Subscribe("cancelAndCloseButton")
    protected void onCancelAndCloseButtonClick(Button.ClickEvent event) {
        customPopupButton.setPopupVisible(false);

        comboBox.setValue(null);
        textField.setValue(null);

        notifications.create()
                .withCaption("Cancelled")
                .show();
    }
}