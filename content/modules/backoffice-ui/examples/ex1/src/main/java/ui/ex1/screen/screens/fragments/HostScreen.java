package ui.ex1.screen.screens.fragments;

import io.jmix.ui.Fragments;
import io.jmix.ui.component.GroupBoxLayout;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.screen.screens.fragments.AddressFragment;

// tag::host-screen[]
@UiController("sample_HostScreen")
@UiDescriptor("host-screen.xml")
public class HostScreen extends Screen {

    @Autowired
    private Fragments fragments; // <1>

    @Autowired
    private GroupBoxLayout addressBox;

    @Subscribe
    private void onInit(InitEvent event) {
        AddressFragment addressFragment = fragments.create(this, AddressFragment.class); // <2>
        addressBox.add(addressFragment.getFragment()); // <3>
    }
}
// end::host-screen[]