package ui.ex1.components.colorbutton;

import com.vaadin.ui.Layout;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import ui.ex1.widgets.ColorButton;
// tag::color-button-screen[]
@UiController("sample_ColorButtonScreen")
@UiDescriptor("color-button-screen.xml")
public class ColorButtonScreen extends Screen {
    @Subscribe
    protected void onInit(InitEvent event) {
        ColorButton button = new ColorButton(); // <1>
        button.setCaption("Button");
        button.setColor("#AFEEEE");
        getWindow().unwrap(Layout.class).addComponent(button); // <2>
    }
}
// end::color-button-screen[]