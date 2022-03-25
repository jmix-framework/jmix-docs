package ui.ex1.screen.screens.fragments;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Fragment;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_HostScreen3")
@UiDescriptor("host-screen3.xml")
public class HostScreen3 extends Screen {

    // tag::fragment-event[]
    @Autowired
    private Notifications notifications;

    @Autowired
    private Fragment addressFragment; // <1>

    @Subscribe(id = "addressFragment", target = Target.CONTROLLER) // <2>
    protected void onChange(AddressFragment.ChangeEvent event) {
        notifications.create()
                .withCaption("Address changed in" + addressFragment.getId())
                .show();
    }
    // end::fragment-event[]

}



