package sample.screen.address;

import io.jmix.ui.screen.*;
import sample.entity.Address;

@UiController("sample_Address.edit")
@UiDescriptor("address-edit.xml")
@EditedEntityContainer("addressDc")
public class AddressEdit extends StandardEditor<Address> {
}