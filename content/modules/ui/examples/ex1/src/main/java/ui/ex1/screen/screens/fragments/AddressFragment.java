package ui.ex1.screen.screens.fragments;

import io.jmix.core.common.event.Subscription;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Address;
import ui.ex1.entity.Country;

import java.util.EventObject;
import java.util.function.Consumer;

// tag::fragment-java1[]
// tag::fragment-java3[]
@UiController("sample_AddressFragment")
@UiDescriptor("address-fragment.xml")
public class AddressFragment extends ScreenFragment {
    // end::fragment-java1[]

    @Autowired
    private TextField<String> zipcodeField;

    private String zipcode;

    // end::fragment-java3[]

    private InstanceContainer<Address> addressInstanceDc;

    // tag::logger[]
    private static final Logger log = LoggerFactory.getLogger(AddressFragment.class);

    // end::logger[]

    @Autowired
    private InstanceContainer<Address> addressDc;

    // tag::fragment-java4[]
    public void setZipcode(String zipcode) { // <1>
        this.zipcode = zipcode;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        zipcodeField.setInputPrompt(zipcode); // <2>
    }

    // end::fragment-java4[]

    public void setAddressInstanceDc(InstanceContainer<Address> addressInstanceDc) {
        this.addressInstanceDc = addressInstanceDc;
    }

    // tag::events[]
    @Subscribe
    private void onAttach(AttachEvent event) {
        Screen hostScreen = getHostScreen();
        FrameOwner hostController = getHostController();
        log.info("onAttach to screen {} with controller {}", hostScreen, hostController);
    }
    // end::events[]

    // tag::target[]
    @Subscribe(target = Target.PARENT_CONTROLLER)
    private void onBeforeShowHost(Screen.BeforeShowEvent event) {
        // ...
    }
    // end::target[]

    // tag::fragment-event[]

    public static class ChangeEvent extends EventObject { // <1>
        public ChangeEvent(Object source) {
            super(source);
        }
    }

    public Subscription addChangeListener(Consumer<ChangeEvent> listener) {
        return getEventHub().subscribe(ChangeEvent.class, listener); // <2>
    }

    @Subscribe("countryField")
    public void onCountryFieldValueChange(HasValue.ValueChangeEvent<Country> event) {
        fireEvent(ChangeEvent.class, new ChangeEvent(this)); // <3>
    }

    // end::fragment-event[]

    // tag::fragment-java2[]
}
// end::fragment-java2[]