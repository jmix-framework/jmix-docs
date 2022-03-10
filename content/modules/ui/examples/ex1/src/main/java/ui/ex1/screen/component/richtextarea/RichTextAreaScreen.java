package ui.ex1.screen.component.richtextarea;

import io.jmix.ui.component.RichTextArea;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_RichTextAreaScreen")
@UiDescriptor("rich-text-area-screen.xml")
public class RichTextAreaScreen extends Screen {
    // tag::rich-text-area[]
    @Autowired
    private RichTextArea richTextArea;

    @Subscribe
    public void onInit(InitEvent event) {
        richTextArea.setValue("<i>Jackdaws </i><u>love</u> " +
                "<font color=\"#0000ff\">my</font> " +
                "<font size=\"7\">big</font> <sup>sphinx</sup> " +
                "<font face=\"Verdana\">of</font> " +
                "<span style=\"background-color: " +
                "red;\">quartz</span>");
    }
    // end::rich-text-area[]
}