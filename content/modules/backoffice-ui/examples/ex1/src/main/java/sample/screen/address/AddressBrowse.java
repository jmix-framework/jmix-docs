package sample.screen.address;

import io.jmix.ui.screen.*;
import sample.entity.Address;

@UiController("sample_Address.browse")
@UiDescriptor("address-browse.xml")
@LookupComponent("addressesTable")
public class AddressBrowse extends StandardLookup<Address> {
}