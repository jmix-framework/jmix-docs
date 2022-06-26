package ui.ex1.screen.screens.fragments;

import io.jmix.ui.Fragments;
import io.jmix.ui.component.GroupBoxLayout;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.screen.screens.fragments.AddressFragment;

@UiController("sample_HostScreen2")
@UiDescriptor("host-screen2.xml")
public class HostScreen2 extends Screen {

    // tag::param[]
    @Autowired
    private Fragments fragments;

    @Autowired
    private GroupBoxLayout addressBox;

    @Subscribe
    private void onInit(InitEvent event) {
        AddressFragment addressFragment = fragments.create(this, AddressFragment.class);
        addressFragment.setZipcode("2779001"); // <1>
        addressBox.add(addressFragment.getFragment());
    }
    // end::param[]
}