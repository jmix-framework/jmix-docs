package dashboards.ex2.screen.town;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.Town;

@UiController("sample_Town.edit")
@UiDescriptor("town-edit.xml")
@EditedEntityContainer("townDc")
public class TownEdit extends StandardEditor<Town> {
}