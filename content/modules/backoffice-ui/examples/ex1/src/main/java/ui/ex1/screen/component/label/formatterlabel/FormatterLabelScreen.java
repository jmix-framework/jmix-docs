package ui.ex1.screen.component.label.formatterlabel;

import io.jmix.core.TimeSource;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@UiController("sample_FormatterLabelScreen")
@UiDescriptor("formatterLabel-screen.xml")
public class FormatterLabelScreen extends Screen {
    @Autowired
    private Label<Date> dateLabel;
    @Autowired
    private Label<Long> numberLabel;
    @Autowired
    private TimeSource timeSource;

    @Subscribe
    protected void onInit(InitEvent event) {
        dateLabel.setValue(timeSource.currentTimestamp());
        numberLabel.setValue(timeSource.currentTimeMillis());
    }
}