package multitenancy.ex1.screen.region;

import io.jmix.ui.screen.*;
import multitenancy.ex1.entity.Region;

@UiController("mtex1_Region.edit")
@UiDescriptor("region-edit.xml")
@EditedEntityContainer("regionDc")
public class RegionEdit extends StandardEditor<Region> {
}