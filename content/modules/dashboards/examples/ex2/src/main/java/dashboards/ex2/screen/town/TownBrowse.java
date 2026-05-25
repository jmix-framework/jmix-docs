package dashboards.ex2.screen.town;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.Town;

@UiController("sample_Town.browse")
@UiDescriptor("town-browse.xml")
@LookupComponent("townsTable")
public class TownBrowse extends StandardLookup<Town> {
}