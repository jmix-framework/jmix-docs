package ui.ex1.screen.screens.fragments;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Address;

@UiController("sample_CountryScreen")
@UiDescriptor("country-screen.xml")
@EditedEntityContainer("addressDc")
public class CountryScreen extends StandardEditor<Address> {
}