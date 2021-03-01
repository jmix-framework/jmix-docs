package ui.ex1.screen.component.textarea;

import io.jmix.ui.component.Label;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("TextAreaScreen")
@UiDescriptor("text-area-screen.xml")
public class TextAreaScreen extends Screen {
    // tag::sized-text-area[]
    @Autowired
    private TextArea sizedTextArea;
    @Autowired
    private Label textAreaLabel;

    @Subscribe
    protected void onInit(InitEvent initEvent) {
        sizedTextArea.addTextChangeListener(event -> {
            int length = event.getText().length();
            textAreaLabel.setValue(length + " of " + sizedTextArea.getMaxLength());
        });
    }
    // end::sized-text-area[]
}