package datamodel.ex1.screen.address;

import io.jmix.ui.screen.*;
import datamodel.ex1.entity.Address;

@UiController("sample_Address.browse")
@UiDescriptor("address-browse.xml")
@LookupComponent("addressesTable")
public class AddressBrowse extends StandardLookup<Address> {
}