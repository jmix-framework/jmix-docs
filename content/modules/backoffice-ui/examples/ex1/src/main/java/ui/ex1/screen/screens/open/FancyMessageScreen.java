package ui.ex1.screen.screens.open;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

// tag::fancy-screen1[]
@UiController("sample_FancyMessageScreen")
@UiDescriptor("fancy-message-screen.xml")
public class FancyMessageScreen extends Screen {

    //end::fancy-screen1[]

    // tag::label[]
    @Autowired
    private Label<String> messageLabel;

    // end::label[]

    // tag::fancy-screen2[]
    public void setFancyMessage(String message) { // <2>
        messageLabel.setValue(message);
    }

    @Subscribe("closeBtn")
    protected void onCloseBtnClick(Button.ClickEvent event) { // <3>
        closeWithDefaultAction();
    }
    // end::fancy-screen2[]

    // tag::on-init[]
    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof FancyMessageOptions) {
            String message = ((FancyMessageOptions) options).getMessage();
            messageLabel.setValue(message);
        }
    }
    // end::on-init[]
    // tag::end[]
}
// end::end[]