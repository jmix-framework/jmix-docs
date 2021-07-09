package rest.sample.screen.product;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import rest.sample.entity.Product;

@UiController("sample_Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
public class ProductEdit extends StandardEditor<Product> {
}