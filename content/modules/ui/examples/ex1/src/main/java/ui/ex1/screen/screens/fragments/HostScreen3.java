package ui.ex1.screen.screens.fragments;

import io.jmix.ui.Notifications;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_HostScreen3")
@UiDescriptor("host-screen3.xml")
public class HostScreen3 extends Screen {

    // tag::fragment-event[]
    @Autowired
    private Notifications notifications;

    @Subscribe(id = "addressFragment", target = Target.CONTROLLER) // <1>
    protected void onChange(AddressFragment.CountryChangeEvent event) {
        notifications.create()
                .withCaption("Changed country: " + event.getCountry())
                .show();
    }
    // end::fragment-event[]

}



