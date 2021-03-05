package ui.ex1.screen.entity.brand;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Brand;

@UiController("uiex1_Brand.browse")
@UiDescriptor("brand-browse.xml")
@LookupComponent("brandsTable")
public class BrandBrowse extends StandardLookup<Brand> {
}