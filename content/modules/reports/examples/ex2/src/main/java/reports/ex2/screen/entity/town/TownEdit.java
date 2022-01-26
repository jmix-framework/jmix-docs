package reports.ex2.screen.entity.town;

import io.jmix.ui.screen.*;
import reports.ex2.entity.Town;

@UiController("jmxrpr_Town.edit")
@UiDescriptor("town-edit.xml")
@EditedEntityContainer("townDc")
public class TownEdit extends StandardEditor<Town> {
}