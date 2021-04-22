package ui.ex1.screen.component.tagpicker;

import io.jmix.ui.component.TagPicker;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@UiController("sample_TagPickerScreen")
@UiDescriptor("tag-picker-screen.xml")
public class TagPickerScreen extends Screen {
    // tag::data-type[]
    @Autowired
    private TagPicker<String> dataTypeTagPicker;

    @Subscribe
    public void onInit(InitEvent event) {
        dataTypeTagPicker.setOptionsList(Arrays.asList("Headphones", "Keyboard", "Mouse"));
    }
    // end::data-type[]
}