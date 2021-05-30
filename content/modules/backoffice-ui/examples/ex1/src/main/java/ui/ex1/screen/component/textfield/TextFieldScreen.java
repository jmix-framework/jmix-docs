package ui.ex1.screen.component.textfield;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.TextInputField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_TextFieldScreen")
@UiDescriptor("textField-screen.xml")
public class TextFieldScreen extends Screen {
    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    // tag::conversion-text-field[]
    @Autowired
    private TextField<Integer> textField;

    // end::conversion-text-field[]

    // tag::styled-text-field[]
    @Autowired
    private TextField<String> styledField;

    // end::styled-text-field[]

    // tag::value-change-event[]
    @Subscribe("textField")
    public void onTextFieldValueChange(HasValue.ValueChangeEvent event) {
        notifications.create()
                .withCaption("Before: " + event.getPrevValue() +
                        ". After: " + event.getValue())
                .show();
    }
    // end::value-change-event[]

    // tag::enter-press-event[]
    @Subscribe("textField")
    public void onTextFieldEnterPress(TextInputField.EnterPressEvent event) {
        notifications.create()
                .withCaption("Enter pressed")
                .show();
    }
    // end::enter-press-event[]

    // tag::text-field-change-event[]
    @Autowired
    private Label<String> shortTextLabel;
    // tag::event-mode[]
    @Autowired
    private TextField<String> shortTextField;

    // end::event-mode[]
    @Subscribe("shortTextField")
    public void onShortTextFieldTextChange(TextInputField.TextChangeEvent event) {
        int length = event.getText().length();
        shortTextLabel.setValue(length + " of " + shortTextField.getMaxLength());
    }

    // end::text-field-change-event[]

    // tag::conversion-text-field[]
    // tag::styled-text-field[]
    // tag::event-mode[]
    @Subscribe
    protected void onInit(InitEvent initEvent) {
        // end::event-mode[]
        // end::conversion-text-field[]
        // end::styled-text-field[]

        // tag::conversion-text-field[]
        textField.setConversionErrorMessage("This field can work only with Integers");
        // end::conversion-text-field[]

        // tag::styled-text-field[]
        styledField.setStyleName("align-center");
        // end::styled-text-field[]

        // tag::event-mode[]
        shortTextField.setTextChangeEventMode(TextInputField.TextChangeEventMode.LAZY);
        // end::event-mode[]

        // tag::conversion-text-field[]
        // tag::styled-text-field[]
        // tag::event-mode[]
    }
    // end::event-mode[]
    // end::conversion-text-field[]
    // end::styled-text-field[]
}