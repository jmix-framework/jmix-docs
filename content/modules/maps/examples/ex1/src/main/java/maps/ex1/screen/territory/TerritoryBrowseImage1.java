package maps.ex1.screen.territory;

import io.jmix.ui.screen.*;
import maps.ex1.entity.Territory;

@UiController("mapst_Territory.browseimage1")
@UiDescriptor("territory-browse-image1.xml")
@LookupComponent("territoriesTable")
public class TerritoryBrowseImage1 extends StandardLookup<Territory> {
}