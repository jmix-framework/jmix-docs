package ui.ex1.screen.component.label.htmllabel;

import io.jmix.ui.component.Label;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_HtmlLabelScreen")
@UiDescriptor("htmlLabel-screen.xml")
public class HtmlLabelScreen extends Screen {
    // tag::html-label[]
    private static final String HTML = "In HTML mode, all HTML formatting tags, such as \n" +
            "<ul>" +
            "  <li><b>bold</b></li>" +
            "  <li>itemized lists</li>" +
            "  <li>etc.</li>" +
            "</ul> " +
            "are preserved.";

    @Autowired
    private Label<String> htmlLabel;

    @Subscribe
    protected void onInit(InitEvent event) {
        htmlLabel.setValue(HTML);
    }
    // end::html-label[]
}