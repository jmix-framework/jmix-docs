package ui.ex1.screen.entity.address;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Address;

@UiController("uiex1_Address.browse")
@UiDescriptor("address-browse.xml")
@LookupComponent("addressesTable")
public class AddressBrowse extends StandardLookup<Address> {
}