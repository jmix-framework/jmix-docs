package com.company.jmixreports.screen.town;

import io.jmix.ui.screen.*;
import com.company.jmixreports.entity.Town;

@UiController("jmxrpr_Town.edit")
@UiDescriptor("town-edit.xml")
@EditedEntityContainer("townDc")
public class TownEdit extends StandardEditor<Town> {
}