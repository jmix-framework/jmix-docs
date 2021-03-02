package com.company.jmixreports.screen.town;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Town;

@UiController("jmxrpr_Town.browse")
@UiDescriptor("town-browse.xml")
@LookupComponent("townsTable")
public class TownBrowse extends StandardLookup<Town> {
}