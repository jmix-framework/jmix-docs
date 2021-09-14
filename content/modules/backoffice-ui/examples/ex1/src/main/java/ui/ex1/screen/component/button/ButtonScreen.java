package ui.ex1.screen.component.button;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("button-screen")
@UiDescriptor("button-screen.xml")
public class ButtonScreen extends Screen {

    @Autowired
    protected Notifications notifications;

    @Autowired
    private Button dependentBtn;

    // tag::click-handler[]
    @Subscribe("helloButton") // <1>
    protected void onHelloButtonClick(Button.ClickEvent event) {
        Button button = event.getSource(); // <2>
        // ...
    }
    // end::click-handler[]

    @Subscribe("saveButton")
    protected void onSaveButtonClick(Button.ClickEvent event) {
        save(event.getSource().getId());
    }

    @Subscribe("saveButton2")
    protected void onSaveButton2Click(Button.ClickEvent event) {
        save(event.getSource().getId());
    }

    public void save(String id) {
        notifications.create()
                .withCaption("Save called from " + id)
                .show();
    }

    @Subscribe("mainBtn")
    public void onMainBtnClick(Button.ClickEvent event) {
        dependentBtn.click();
    }

    @Subscribe("dependentBtn")
    public void onDependentBtnClick(Button.ClickEvent event) {
        notifications.create()
                .withCaption("This event was sent by the Dependent button")
                .show();
    }
}
