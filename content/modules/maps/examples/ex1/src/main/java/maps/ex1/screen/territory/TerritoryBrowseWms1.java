package maps.ex1.screen.territory;

import io.jmix.ui.screen.*;
import maps.ex1.entity.Territory;

@UiController("mapst_Territory.browsewms1")
@UiDescriptor("territory-browse-wms1.xml")
@LookupComponent("territoriesTable")
public class TerritoryBrowseWms1 extends StandardLookup<Territory> {
}