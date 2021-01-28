package maps.ex1.screen.territory;

import io.jmix.ui.screen.*;
import maps.ex1.entity.Territory;

@UiController("mapst_Territory.browsetile1")
@UiDescriptor("territory-browse-tile1.xml")
@LookupComponent("territoriesTable")
public class TerritoryBrowseTile1 extends StandardLookup<Territory> {
}