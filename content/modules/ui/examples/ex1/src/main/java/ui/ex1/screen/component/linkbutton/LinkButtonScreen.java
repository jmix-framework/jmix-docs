package ui.ex1.screen.component.linkbutton;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_LinkButtonScreen")
@UiDescriptor("link-button-screen.xml")
public class LinkButtonScreen extends Screen {
    @Autowired
    protected Notifications notifications;

    @Subscribe("saveButton")
    public void onSaveButtonClick(Button.ClickEvent event) {
        save(event.getSource().getId());
    }

    @Subscribe("saveButton1")
    protected void onSaveButton1Click(Button.ClickEvent event) {
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
}