package ui.ex1.screen.component.textfield;
import io.jmix.ui.Notifications;
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
    // tag::text-field-change-listener[]
    @Autowired
    private TextField<String> shortTextField;
    @Autowired
    private Label<String> shortTextLabel;

    // end::text-field-change-listener[]

    // tag::text-field-press-listener[]
    @Autowired
    private Notifications notifications;
    // end::text-field-press-listener[]

    // tag::text-field-press-listener[]
    // tag::conversion-text-field[]
    @Autowired
    private TextField<Integer> textField;

    // end::conversion-text-field[]
    // end::text-field-press-listener[]

    // tag::styled-text-field[]
    @Autowired
    private TextField<String> styledField;

    // end::styled-text-field[]

    // tag::value-change-listener[]
    @Autowired
    private TextField<Integer> integerField;

    // end::value-change-listener[]

    // tag::value-change-listener[]
    // tag::conversion-text-field[]
    // tag::styled-text-field[]
    // tag::text-field-change-listener[]
    // tag::text-field-press-listener[]
    @Subscribe
    protected void onInit(InitEvent initEvent) {
        // end::conversion-text-field[]
        // end::styled-text-field[]
        // end::text-field-change-listener[]
        // end::text-field-press-listener[]
        // end::value-change-listener[]

        // tag::conversion-text-field[]
        textField.setConversionErrorMessage("This field can work only with Integers");
        // end::conversion-text-field[]

        // tag::styled-text-field[]
        styledField.setStyleName("align-center");
        // end::styled-text-field[]

        // tag::text-field-change-listener[]
        shortTextField.addTextChangeListener(event -> {
            int length = event.getText().length();
            shortTextLabel.setValue(length + " of " + shortTextField.getMaxLength());
        });
        shortTextField.setTextChangeEventMode(TextInputField.TextChangeEventMode.LAZY);
        // end::text-field-change-listener[]

        // tag::text-field-press-listener[]

        textField.addEnterPressListener(enterPressEvent ->
                notifications.create()
                        .withCaption("Enter pressed")
                        .show());

        // end::text-field-press-listener[]

        // tag::value-change-listener[]
        integerField.addValueChangeListener(stringValueChangeEvent ->
                notifications.create()
                        .withCaption("Before: " + stringValueChangeEvent.getPrevValue() +
                                ". After: " + stringValueChangeEvent.getValue())
                        .show());
        // end::value-change-listener[]

        // tag::value-change-listener[]
        // tag::conversion-text-field[]
        // tag::styled-text-field[]
        // tag::text-field-change-listener[]
        // tag::text-field-press-listener[]
    }
    // end::value-change-listener[]
    // end::conversion-text-field[]
    // end::styled-text-field[]
    // end::text-field-change-listener[]
    // end::text-field-press-listener[]
}