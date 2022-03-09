package ui.ex1.screen.component.form;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.data.value.ContainerValueSource;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Customer;

@UiController("FormScreen")
@UiDescriptor("form-screen.xml")
public class FormScreen extends Screen {
    // tag::form[]
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private InstanceContainer<Customer> customerDc;
    @Autowired
    private Form form;

    @Subscribe
    private void onInit(InitEvent event) {
        TextField<String> emailField = uiComponents.create(TextField.TYPE_STRING);
        emailField.setCaption("Email");
        emailField.setWidthFull();
        emailField.setValueSource(new ContainerValueSource<>(customerDc, "email"));
        form.add(emailField);
    }
    // end::form[]
}