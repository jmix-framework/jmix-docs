package ui.ex1.screen.component.textfield;
import io.jmix.core.Metadata;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.*;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

@UiController("sample_TextFieldScreen")
@UiDescriptor("textField-screen.xml")
public class TextFieldScreen extends Screen {
    @Autowired
    protected Metadata metadata;
    @Autowired
    protected InstanceContainer<Customer> customerDc;
    @Autowired
    protected TextField<Integer> textValidField;
    @Autowired
    protected TextField<Integer> zipField;
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
        Customer customer = metadata.create(Customer.class);
        customer.setFirstName("John");
        customer.setLastName("Bates");
        customer.setAge(35);
        customerDc.setItem(customer);
        // tag::conversion-text-field[]
        // tag::styled-text-field[]
        // tag::event-mode[]
    }
    // end::event-mode[]
    // end::conversion-text-field[]
    // end::styled-text-field[]

    // tag::formatter[]
    @Install(to = "customerField", subject = "formatter")
    protected String customerFieldFormatter(String value) {
        return value.toUpperCase();
    }
    // end::formatter[]

    @Subscribe("validBtn")
    protected void onValidBtnClick(Button.ClickEvent event) {
        textValidField.validate();
    }

    @Subscribe("validateBtn")
    protected void onValidateBtnClick(Button.ClickEvent event) {
        zipField.validate();
    }
    // tag::validator[]
    @Install(to = "zipField", subject = "validator")
    protected void zipFieldValidator(Integer value) {
        if (value != null && String.valueOf(value).length() != 6)
            throw new ValidationException("Zip must be of 6 digits length");
    }
    // end::validator[]
}