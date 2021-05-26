package ui.ex1.screen.screens.fragments;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Address;

@UiController("uiex1_Address1.browse")
@UiDescriptor("address-browse1.xml")
@LookupComponent("addressesTable")
public class AddressBrowse1 extends StandardLookup<Address> {
}