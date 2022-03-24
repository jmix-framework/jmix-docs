package ui.ex1.screen.screens.fragments;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Address;

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
    private InstanceContainer<Address> containerParam;
    private UiComponents componentParam;

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

    public void setContainerParam(InstanceContainer<Address> containerParam) {
        this.containerParam = containerParam;
    }

    public InstanceContainer<Address> getContainerParam(){
        return this.containerParam;
    }

    public UiComponents getComponentParam() {
        return componentParam;
    }

    public void setComponentParam(UiComponents componentParam) {
        this.componentParam = componentParam;
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
    // tag::fragment-java2[]
}
// end::fragment-java2[]