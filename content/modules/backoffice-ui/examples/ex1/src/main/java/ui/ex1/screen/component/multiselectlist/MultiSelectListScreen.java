package ui.ex1.screen.component.multiselectlist;

import io.jmix.ui.component.MultiSelectList;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Hobby;

import java.util.Arrays;

@UiController("multiSelectList-screen")
@UiDescriptor("multiselectlist-screen.xml")
public class MultiSelectListScreen extends Screen {

    // tag::set-values[]
    @Autowired
    private MultiSelectList<Hobby> hobbyList;

    @Subscribe
    public void onInit(InitEvent event) {
        hobbyList.setValue(Arrays.asList(Hobby.FAMILY, Hobby.COMPUTER));
    }
    // end::set-values[]
}