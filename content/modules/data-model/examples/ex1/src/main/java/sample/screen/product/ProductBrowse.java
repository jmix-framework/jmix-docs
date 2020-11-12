package sample.screen.product;

import io.jmix.ui.screen.*;
import sample.entity.Product;

@UiController("sample_Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
public class ProductBrowse extends StandardLookup<Product> {
}