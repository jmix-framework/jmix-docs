package ui.ex1.screen.screens;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Order;

@UiController("sample_SampleScreen")
@UiDescriptor("sample-screen.xml")
@EditedEntityContainer("orderDc")
public class SampleScreen extends StandardEditor<Order> {
}