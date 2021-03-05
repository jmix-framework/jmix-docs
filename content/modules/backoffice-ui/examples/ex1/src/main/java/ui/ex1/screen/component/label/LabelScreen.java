package ui.ex1.screen.component.label;

import io.jmix.core.Metadata;
import io.jmix.ui.component.Label;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

@UiController("sample_LabelScreen")
@UiDescriptor("label-screen.xml")
public class LabelScreen extends Screen {
    @Autowired
    private InstanceContainer<Customer> customerDc;
    @Autowired
    private Metadata metadata;
    // tag::dynamic-label[]
    @Autowired
    private Label<String> dynamicLabel;

    // end::dynamic-label[]
    // tag::styled-label[]
    @Autowired
    private Label<String> styledLabel;

    // end::styled-label[]
    // tag::html-label[]
    @Autowired
    private Label<String> htmlLabel;

    private static final String UNSAFE_HTML = "<i>Jackdaws </i><u>love</u> " +
            "<font size=\"javascript:alert(1)\" " +
            "color=\"moccasin\">my</font> " +
            "<font size=\"7\">big</font> <sup>sphinx</sup> " +
            "<font face=\"Verdana\">of</font> <span style=\"background-color: " +
            "red;\">quartz</span><svg/onload=alert(\"XSS\")>";

    // end::html-label[]

    // tag::html-label[]
    // tag::dynamic-label[]
    // tag::styled-label[]
    @Subscribe
    protected void onInit(InitEvent event) {
        // end::dynamic-label[]
        // end::html-label[]
        // end::styled-label[]

        // InstanceContainer initialization. It is usually done automatically if the screen is
        // inherited from StandardEditor and is used as an entity editor.
        Customer customer = metadata.create(Customer.class);
        customer.setFirstName("John");
        customerDc.setItem(customer);

        // tag::dynamic-label[]
        dynamicLabel.setValue("Some value");
        // end::dynamic-label[]

        // tag::html-label[]
        htmlLabel.setHtmlEnabled(true);
        htmlLabel.setHtmlSanitizerEnabled(true);
        htmlLabel.setValue(UNSAFE_HTML);
        // end::html-label[]

        // tag::styled-label[]
        styledLabel.setStyleName("bold");
        // end::styled-label[]

        // tag::html-label[]
        // tag::dynamic-label[]
        // tag::styled-label[]
    }
    // end::styled-label[]
    // end::html-label[]
    // end::dynamic-label[]
}