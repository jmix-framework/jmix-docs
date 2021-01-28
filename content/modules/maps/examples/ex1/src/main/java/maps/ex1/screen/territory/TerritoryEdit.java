package maps.ex1.screen.territory;

import io.jmix.ui.screen.*;
import maps.ex1.entity.Territory;

@UiController("mapst_Territory.edit")
@UiDescriptor("territory-edit.xml")
@EditedEntityContainer("territoryDc")
public class TerritoryEdit extends StandardEditor<Territory> {
}