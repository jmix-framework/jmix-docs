package ui.ex1.screen.entity.brand;

import io.jmix.ui.screen.*;
import ui.ex1.entity.Brand;

@UiController("uiex1_Brand.edit")
@UiDescriptor("brand-edit.xml")
@EditedEntityContainer("brandDc")
public class BrandEdit extends StandardEditor<Brand> {
}