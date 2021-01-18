package datamodel.ex1.screen.address;

import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Address;

@UiController("sample_Address.edit")
@UiDescriptor("address-edit.xml")
@EditedEntityContainer("addressDc")
public class AddressEdit extends StandardEditor<Address> {
}