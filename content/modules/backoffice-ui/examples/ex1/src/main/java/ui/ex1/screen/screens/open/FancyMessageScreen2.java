package ui.ex1.screen.screens.open;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_FancyMessageScreen2")
@UiDescriptor("fancy-message-screen2.xml")
public class FancyMessageScreen2 extends Screen {

    // tag::label[]
    @Autowired
    private Label<String> messageLabel;

    // end::label[]

    public void setFancyMessage(String message) {
        messageLabel.setValue(message);
    }

    @Subscribe("closeBtn")
    protected void onCloseBtnClick(Button.ClickEvent event) {
        closeWithDefaultAction();
    }

    // tag::on-init[]
    @Subscribe
    private void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof MapScreenOptions) {
            String message = (String) ((MapScreenOptions) options).getParams().get("message");
            messageLabel.setValue(message);
        }
    }
    // end::on-init[]
}
