package multitenancy.ex1.screen.region;

import io.jmix.ui.screen.*;
import multitenancy.ex1.entity.Region;

@UiController("mtex1_Region.browse")
@UiDescriptor("region-browse.xml")
@LookupComponent("regionsTable")
public class RegionBrowse extends StandardLookup<Region> {
}